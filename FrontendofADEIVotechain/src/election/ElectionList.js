import React, { Component } from 'react';
import { getAllElections, getUserCreatedElections, getUserVotedElections } from '../util/APIUtils';
import Election from './Election';
import { castVote } from '../util/APIUtils';
import LoadingIndicator  from '../common/LoadingIndicator';
import { Button, Icon, notification } from 'antd';
import { ELECTION_LIST_SIZE } from '../constants';
import { withRouter } from 'react-router-dom';
import './ElectionList.css';

class ElectionList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            elections: [],
            page: 0,
            size: 10,
            totalElements: 0,
            totalPages: 0,
            last: true,
            currentVotes: [],
            isLoading: false
        };
        this.loadElectionList = this.loadElectionList.bind(this);
        this.handleLoadMore = this.handleLoadMore.bind(this);
    }

    loadElectionList(page = 0, size = ELECTION_LIST_SIZE) {
        let promise;
        if(this.props.apogeecode) {
            if(this.props.type === 'USER_CREATED_ELECTIONS') {
                promise = getUserCreatedElections(this.props.apogeecode, page, size);
            } else if (this.props.type === 'USER_VOTED_ELECTIONS') {
                promise = getUserVotedElections(this.props.apogeecode, page, size);                               
            }
        } else {
            promise = getAllElections(page, size);
        }

        if(!promise) {
            return;
        }

        this.setState({
            isLoading: true
        });

        promise            
        .then(response => {
            const elections = this.state.elections.slice();
            const currentVotes = this.state.currentVotes.slice();

            this.setState({
                elections: elections.concat(response.content),
                page: response.page,
                size: response.size,
                totalElements: response.totalElements,
                totalPages: response.totalPages,
                last: response.last,
                currentVotes: currentVotes.concat(Array(response.content.length).fill(null)),
                isLoading: false
            })
        }).catch(error => {
            this.setState({
                isLoading: false
            })
        });  
        
    }

    componentDidMount() {
        this.loadElectionList();
    }

    componentDidUpdate(nextProps) {
        if(this.props.isAuthenticated !== nextProps.isAuthenticated) {
            // Reset State
            this.setState({
                elections: [],
                page: 0,
                size: 10,
                totalElements: 0,
                totalPages: 0,
                last: true,
                currentVotes: [],
                isLoading: false
            });    
            this.loadElectionList();
        }
    }

    handleLoadMore() {
        this.loadElectionList(this.state.page + 1);
    }

    handleVoteChange(event, electionIndex) {
        const currentVotes = this.state.currentVotes.slice();
        currentVotes[electionIndex] = event.target.value;

        this.setState({
            currentVotes: currentVotes
        });
    }


    handleVoteSubmit(event, electionIndex) {
        event.preventDefault();
        if(!this.props.isAuthenticated) {
            this.props.history.push("/login");
            notification.info({
                message: 'Electioning App',
                description: "Please login to vote.",          
            });
            return;
        }

        const election = this.state.elections[electionIndex];
        const selectedCandidate = this.state.currentVotes[electionIndex];

        const voteData = {
            electionId: election.id,
            candidateId: selectedCandidate
        };

        castVote(voteData)
        .then(response => {
            const elections = this.state.elections.slice();
            elections[electionIndex] = response;
            this.setState({
                elections: elections
            });        
        }).catch(error => {
            if(error.status === 401) {
                this.props.handleLogout('/login', 'error', 'You have been logged out. Please login to vote');    
            } else {
                notification.error({
                    message: 'Electioning App',
                    description: error.message || 'Sorry! Something went wrong. Please try again!'
                });                
            }
        });
    }

    render() {
        const electionViews = [];
        this.state.elections.forEach((election, electionIndex) => {
            electionViews.push(<Election 
                key={election.id} 
                election={election}
                currentVote={this.state.currentVotes[electionIndex]} 
                handleVoteChange={(event) => this.handleVoteChange(event, electionIndex)}
                handleVoteSubmit={(event) => this.handleVoteSubmit(event, electionIndex)} />)            
        });

        return (
            <div className="elections-container">
                {electionViews}
                {
                    !this.state.isLoading && this.state.elections.length === 0 ? (
                        <div className="no-elections-found">
                            <span>No Elections Found.</span>
                        </div>    
                    ): null
                }  
                {
                    !this.state.isLoading && !this.state.last ? (
                        <div className="load-more-elections"> 
                            <Button type="dashed" onClick={this.handleLoadMore} disabled={this.state.isLoading}>
                                <Icon type="plus" /> Load more
                            </Button>
                        </div>): null
                }              
                {
                    this.state.isLoading ? 
                    <LoadingIndicator />: null                     
                }
            </div>
        );
    }
}

export default withRouter(ElectionList);
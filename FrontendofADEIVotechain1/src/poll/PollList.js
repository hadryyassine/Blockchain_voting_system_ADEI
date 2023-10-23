import React, { Component } from 'react';
import { getAllPolls, getUserCreatedPolls, getUserVotedPolls } from '../util/APIUtils';
import Poll from './Poll';
import { castVote } from '../util/APIUtils';
import LoadingIndicator  from '../common/LoadingIndicator';
import { Button, Icon, notification } from 'antd';
import { POLL_LIST_SIZE } from '../constants';
import { withRouter } from 'react-router-dom';
import './PollList.css';

class PollList extends Component {
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
        this.loadPollList = this.loadPollList.bind(this);
        this.handleLoadMore = this.handleLoadMore.bind(this);
    }

    loadPollList(page = 0, size = POLL_LIST_SIZE) {
        let promise;
        if(this.props.username) {
            if(this.props.type === 'USER_CREATED_POLLS') {
                promise = getUserCreatedPolls(this.props.username, page, size);
            } else if (this.props.type === 'USER_VOTED_POLLS') {
                promise = getUserVotedPolls(this.props.username, page, size);                               
            }
        } else {
            promise = getAllPolls(page, size);
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
        this.loadPollList();
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
            this.loadPollList();
        }
    }

    handleLoadMore() {
        this.loadPollList(this.state.page + 1);
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
                message: 'ADEI Votechain',
                description: "Please login to vote.",          
            });
            return;
        }

        const election = this.state.elections[electionIndex];
        const selectedChoice = this.state.currentVotes[electionIndex];

        const voteData = {
            electionId: election.id,
            candidateId: selectedChoice
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
                    message: 'ADEI Votechain',
                    description: error.message || 'Sorry! Something went wrong. Please try again!'
                });                
            }
        });
    }

    render() {
        const electionViews = [];
        this.state.elections.forEach((election, electionIndex) => {
            electionViews.push(<Poll 
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
                            <span>No Polls Found.</span>
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

export default withRouter(PollList);
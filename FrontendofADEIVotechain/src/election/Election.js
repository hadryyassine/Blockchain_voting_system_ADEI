import React, { Component } from 'react';
import './Election.css';
import { Avatar, Icon } from 'antd';
import { Link } from 'react-router-dom';
import { getAvatarColor } from '../util/Colors';
import { formatDateTime } from '../util/Helpers';

import { Radio, Button } from 'antd';
const RadioGroup = Radio.Group;

class Election extends Component {
    calculatePercentage = (candidate) => {
        if(this.props.election.totalVotes === 0) {
            return 0;
        }
        return (candidate.voteCount*100)/(this.props.election.totalVotes);
    };

    isSelected = (candidate) => {
        return this.props.election.selectedCandidate === candidate.id;
    }

    getWinningCandidate = () => {
        return this.props.election.candidates.reduce((prevCandidate, currentCandidate) => 
            currentCandidate.voteCount > prevCandidate.voteCount ? currentCandidate : prevCandidate, 
            {voteCount: -Infinity}
        );
    }

    getTimeRemaining = (election) => {
        const expirationTime = new Date(election.expirationDateTime).getTime();
        const currentTime = new Date().getTime();
    
        var difference_ms = expirationTime - currentTime;
        var seconds = Math.floor( (difference_ms/1000) % 60 );
        var minutes = Math.floor( (difference_ms/1000/60) % 60 );
        var hours = Math.floor( (difference_ms/(1000*60*60)) % 24 );
        var days = Math.floor( difference_ms/(1000*60*60*24) );
    
        let timeRemaining;
    
        if(days > 0) {
            timeRemaining = days + " days left";
        } else if (hours > 0) {
            timeRemaining = hours + " hours left";
        } else if (minutes > 0) {
            timeRemaining = minutes + " minutes left";
        } else if(seconds > 0) {
            timeRemaining = seconds + " seconds left";
        } else {
            timeRemaining = "less than a second left";
        }
        
        return timeRemaining;
    }

    render() {
        const electionCandidates = [];
        if(this.props.election.selectedCandidate || this.props.election.expired) {
            const winningCandidate = this.props.election.expired ? this.getWinningCandidate() : null;

            this.props.election.candidates.forEach(candidate => {
                electionCandidates.push(<CompletedOrVotedElectionCandidate 
                    key={candidate.id} 
                    candidate={candidate}
                    isWinner={winningCandidate && candidate.id === winningCandidate.id}
                    isSelected={this.isSelected(candidate)}
                    percentVote={this.calculatePercentage(candidate)} 
                />);
            });                
        } else {
            this.props.election.candidates.forEach(candidate => {
                electionCandidates.push(<Radio className="election-candidate-radio" key={candidate.id} value={candidate.id}>{candidate.name}</Radio>)
            })    
        }        
        return (
            <div className="election-content">
                <div className="election-header">
                    <div className="election-creator-info">
                        <Link className="creator-link" to={`/users/${this.props.election.createdBy.apogeecode}`}>
                            <Avatar className="election-creator-avatar" 
                                style={{ backgroundColor: getAvatarColor(this.props.election.createdBy.name)}} >
                                {this.props.election.createdBy.name[0].toUpperCase()}
                            </Avatar>
                            <span className="election-creator-name">
                                {this.props.election.createdBy.name}
                            </span>
                            <span className="election-creator-apogeecode">
                                @{this.props.election.createdBy.apogeecode}
                            </span>
                            <span className="election-creation-date">
                                {formatDateTime(this.props.election.creationDateTime)}
                            </span>
                        </Link>
                    </div>
                    <div className="election-positiontitle">
                        {this.props.election.positiontitle}
                    </div>
                </div>
                <div className="election-candidates">
                    <RadioGroup 
                        className="election-candidate-radio-group" 
                        onChange={this.props.handleVoteChange} 
                        value={this.props.currentVote}>
                        { electionCandidates }
                    </RadioGroup>
                </div>
                <div className="election-footer">
                    { 
                        !(this.props.election.selectedCandidate || this.props.election.expired) ?
                        (<Button className="vote-button" disabled={!this.props.currentVote} onClick={this.props.handleVoteSubmit}>Vote</Button>) : null 
                    }
                    <span className="total-votes">{this.props.election.totalVotes} votes</span>
                    <span className="separator">•</span>
                    <span className="time-left">
                        {
                            this.props.election.expired ? "Final results" :
                            this.getTimeRemaining(this.props.election)
                        }
                    </span>
                </div>
            </div>
        );
    }
}

function CompletedOrVotedElectionCandidate(props) {
    return (
        <div className="cv-election-candidate">
            <span className="cv-election-candidate-details">
                <span className="cv-candidate-percentage">
                    {Math.round(props.percentVote * 100) / 100}%
                </span>            
                <span className="cv-candidate-name">
                    {props.candidate.name}
                </span>
                {
                    props.isSelected ? (
                    <Icon
                        className="selected-candidate-icon"
                        type="check-circle-o"
                    /> ): null
                }    
            </span>
            <span className={props.isWinner ? 'cv-candidate-percent-chart winner': 'cv-candidate-percent-chart'} 
                style={{width: props.percentVote + '%' }}>
            </span>
        </div>
    );
}


export default Election;
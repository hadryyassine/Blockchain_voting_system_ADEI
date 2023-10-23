import React, { Component } from 'react';
import './Poll.css';
import { Avatar, Icon } from 'antd';
import { Link } from 'react-router-dom';
import { getAvatarColor } from '../util/Colors';
import { formatDateTime } from '../util/Helpers';

import { Radio, Button } from 'antd';
const RadioGroup = Radio.Group;

class Poll extends Component {
    calculatePercentage = (candidate) => {
        if(this.props.election.totalVotes === 0) {
            return 0;
        }
        return (candidate.voteCount*100)/(this.props.election.totalVotes);
    };

    isSelected = (candidate) => {
        return this.props.election.selectedChoice === candidate.id;
    }

    getWinningChoice = () => {
        return this.props.election.candidates.reduce((prevChoice, currentChoice) => 
            currentChoice.voteCount > prevChoice.voteCount ? currentChoice : prevChoice, 
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
        const electionChoices = [];
        if(this.props.election.selectedChoice || this.props.election.expired) {
            const winningChoice = this.props.election.expired ? this.getWinningChoice() : null;

            this.props.election.candidates.forEach(candidate => {
                electionChoices.push(<CompletedOrVotedPollChoice 
                    key={candidate.id} 
                    candidate={candidate}
                    isWinner={winningChoice && candidate.id === winningChoice.id}
                    isSelected={this.isSelected(candidate)}
                    percentVote={this.calculatePercentage(candidate)} 
                />);
            });                
        } else {
            this.props.election.candidates.forEach(candidate => {
                electionChoices.push(<Radio className="election-candidate-radio" key={candidate.id} value={candidate.id}>{candidate.name}</Radio>)
            })    
        }        
        return (
            <div className="election-content">
                <div className="election-header">
                    <div className="election-creator-info">
                        <Link className="creator-link" to={`/users/${this.props.election.createdBy.username}`}>
                            <Avatar className="election-creator-avatar" 
                                style={{ backgroundColor: getAvatarColor(this.props.election.createdBy.name)}} >
                                {this.props.election.createdBy.name[0].toUpperCase()}
                            </Avatar>
                            <span className="election-creator-name">
                                {this.props.election.createdBy.name}
                            </span>
                            <span className="election-creator-username">
                                @{this.props.election.createdBy.username}
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
                        { electionChoices }
                    </RadioGroup>
                </div>
                <div className="election-footer">
                    { 
                        !(this.props.election.selectedChoice || this.props.election.expired) ?
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

function CompletedOrVotedPollChoice(props) {
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


export default Poll;
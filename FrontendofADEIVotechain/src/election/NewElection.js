import React, { Component } from 'react';
import { createElection } from '../util/APIUtils';
import { MAX_CANDIDATES, ELECTION_QUESTION_MAX_LENGTH, ELECTION_CANDIDATE_MAX_LENGTH } from '../constants';
import './NewElection.css';  
import { Form, Input, Button, Icon, Select, Col, notification } from 'antd';
const Option = Select.Option;
const FormItem = Form.Item;
const { TextArea } = Input

class NewElection extends Component {
    constructor(props) {
        super(props);
        this.state = {
            positiontitle: {
                text: ''
            },
            candidates: [{
                text: ''
            }, {
                text: ''
            }],
            electionLength: {
                days: 1,
                hours: 0
            }
        };
        this.addCandidate = this.addCandidate.bind(this);
        this.removeCandidate = this.removeCandidate.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleQuestionChange = this.handleQuestionChange.bind(this);
        this.handleCandidateChange = this.handleCandidateChange.bind(this);
        this.handleElectionDaysChange = this.handleElectionDaysChange.bind(this);
        this.handleElectionHoursChange = this.handleElectionHoursChange.bind(this);
        this.isFormInvalid = this.isFormInvalid.bind(this);
    }

    addCandidate(event) {
        const candidates = this.state.candidates.slice();        
        this.setState({
            candidates: candidates.concat([{
                text: ''
            }])
        });
    }

    removeCandidate(candidateNumber) {
        const candidates = this.state.candidates.slice();
        this.setState({
            candidates: [...candidates.slice(0, candidateNumber), ...candidates.slice(candidateNumber+1)]
        });
    }

    handleSubmit(event) {
        event.preventDefault();
        const electionData = {
            positiontitle: this.state.positiontitle.text,
            candidates: this.state.candidates.map(candidate => {
                return {text: candidate.text} 
            }),
            electionLength: this.state.electionLength
        };

        createElection(electionData)
        .then(response => {
            this.props.history.push("/");
        }).catch(error => {
            if(error.status === 401) {
                this.props.handleLogout('/login', 'error', 'You have been logged out. Please login create election.');    
            } else {
                notification.error({
                    message: 'Electioning App',
                    description: error.message || 'Sorry! Something went wrong. Please try again!'
                });              
            }
        });
    }

    validateQuestion = (positiontitleText) => {
        if(positiontitleText.length === 0) {
            return {
                validateStatus: 'error',
                errorMsg: 'Please enter your positiontitle!'
            }
        } else if (positiontitleText.length > ELECTION_QUESTION_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Question is too long (Maximum ${ELECTION_QUESTION_MAX_LENGTH} characters allowed)`
            }    
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    }

    handleQuestionChange(event) {
        const value = event.target.value;
        this.setState({
            positiontitle: {
                text: value,
                ...this.validateQuestion(value)
            }
        });
    }

    validateCandidate = (candidateText) => {
        if(candidateText.length === 0) {
            return {
                validateStatus: 'error',
                errorMsg: 'Please enter a candidate!'
            }
        } else if (candidateText.length > ELECTION_CANDIDATE_MAX_LENGTH) {
            return {
                validateStatus: 'error',
                errorMsg: `Candidate is too long (Maximum ${ELECTION_CANDIDATE_MAX_LENGTH} characters allowed)`
            }    
        } else {
            return {
                validateStatus: 'success',
                errorMsg: null
            }
        }
    }

    handleCandidateChange(event, index) {
        const candidates = this.state.candidates.slice();
        const value = event.target.value;

        candidates[index] = {
            text: value,
            ...this.validateCandidate(value)
        }

        this.setState({
            candidates: candidates
        });
    }


    handleElectionDaysChange(value) {
        const electionLength = Object.assign(this.state.electionLength, {days: value});
        this.setState({
            electionLength: electionLength
        });
    }

    handleElectionHoursChange(value) {
        const electionLength = Object.assign(this.state.electionLength, {hours: value});
        this.setState({
            electionLength: electionLength
        });
    }

    isFormInvalid() {
        if(this.state.positiontitle.validateStatus !== 'success') {
            return true;
        }
    
        for(let i = 0; i < this.state.candidates.length; i++) {
            const candidate = this.state.candidates[i];            
            if(candidate.validateStatus !== 'success') {
                return true;
            }
        }
    }

    render() {
        const candidateViews = [];
        this.state.candidates.forEach((candidate, index) => {
            candidateViews.push(<ElectionCandidate key={index} candidate={candidate} candidateNumber={index} removeCandidate={this.removeCandidate} handleCandidateChange={this.handleCandidateChange}/>);
        });

        return (
            <div className="new-election-container">
                <h1 className="page-title">Create Election</h1>
                <div className="new-election-content">
                    <Form onSubmit={this.handleSubmit} className="create-election-form">
                        <FormItem validateStatus={this.state.positiontitle.validateStatus}
                            help={this.state.positiontitle.errorMsg} className="election-form-row">
                        <TextArea 
                            placeholder="Enter your position title"
                            style = {{ fontSize: '16px' }} 
                            autosize={{ minRows: 3, maxRows: 6 }} 
                            name = "positiontitle"
                            value = {this.state.positiontitle.text}
                            onChange = {this.handleQuestionChange} />
                        </FormItem>
                        {candidateViews}
                        <FormItem className="election-form-row">
                            <Button type="dashed" onClick={this.addCandidate} disabled={this.state.candidates.length === MAX_CANDIDATES}>
                                <Icon type="plus" /> Add a candidate
                            </Button>
                        </FormItem>
                        <FormItem className="election-form-row">
                            <Col xs={24} sm={4}>
                                Election length: 
                            </Col>
                            <Col xs={24} sm={20}>    
                                <span style = {{ marginRight: '18px' }}>
                                    <Select 
                                        name="days"
                                        defaultValue="1" 
                                        onChange={this.handleElectionDaysChange}
                                        value={this.state.electionLength.days}
                                        style={{ width: 60 }} >
                                        {
                                            Array.from(Array(8).keys()).map(i => 
                                                <Option key={i}>{i}</Option>                                        
                                            )
                                        }
                                    </Select> &nbsp;Days
                                </span>
                                <span>
                                    <Select 
                                        name="hours"
                                        defaultValue="0" 
                                        onChange={this.handleElectionHoursChange}
                                        value={this.state.electionLength.hours}
                                        style={{ width: 60 }} >
                                        {
                                            Array.from(Array(24).keys()).map(i => 
                                                <Option key={i}>{i}</Option>                                        
                                            )
                                        }
                                    </Select> &nbsp;Hours
                                </span>
                            </Col>
                        </FormItem>
                        <FormItem className="election-form-row">
                            <Button type="primary" 
                                htmlType="submit" 
                                size="large" 
                                disabled={this.isFormInvalid()}
                                className="create-election-form-button">Create Election</Button>
                        </FormItem>
                    </Form>
                </div>    
            </div>
        );
    }
}

function ElectionCandidate(props) {
    return (
        <FormItem validateStatus={props.candidate.validateStatus}
        help={props.candidate.errorMsg} className="election-form-row">
            <Input 
                placeholder = {'Candidate ' + (props.candidateNumber + 1)}
                size="large"
                value={props.candidate.text} 
                className={ props.candidateNumber > 1 ? "optional-candidate": null}
                onChange={(event) => props.handleCandidateChange(event, props.candidateNumber)} />

            {
                props.candidateNumber > 1 ? (
                <Icon
                    className="dynamic-delete-button"
                    type="close"
                    disabled={props.candidateNumber <= 1}
                    onClick={() => props.removeCandidate(props.candidateNumber)}
                /> ): null
            }    
        </FormItem>
    );
}


export default NewElection;
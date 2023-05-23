import { API_BASE_URL, ELECTION_LIST_SIZE, ACCESS_TOKEN } from '../constants';

const request = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    })
    
    if(localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);

    return fetch(options.url, options)
    .then(response => 
        response.json().then(json => {
            if(!response.ok) {
                return Promise.reject(json);
            }
            return json;
        })
    );
};

export function getAllElections(page, size) {
    page = page || 0;
    size = size || ELECTION_LIST_SIZE;

    return request({
        url: API_BASE_URL + "/elections?page=" + page + "&size=" + size,
        method: 'GET'
    });
}

export function createElection(electionData) {
    return request({
        url: API_BASE_URL + "/elections",
        method: 'POST',
        body: JSON.stringify(electionData)         
    });
}

export function castVote(voteData) {
    return request({
        url: API_BASE_URL + "/elections/" + voteData.electionId + "/votes",
        method: 'POST',
        body: JSON.stringify(voteData)
    });
}

export function login(loginRequest) {
    return request({
        url: API_BASE_URL + "/auth/signin",
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function signup(signupRequest) {
    return request({
        url: API_BASE_URL + "/auth/signup",
        method: 'POST',
        body: JSON.stringify(signupRequest)
    });
}

export function checkApogeecodeAvailability(apogeecode) {
    return request({
        url: API_BASE_URL + "/user/checkApogeecodeAvailability?apogeecode=" + apogeecode,
        method: 'GET'
    });
}

export function checkEmailAvailability(email) {
    return request({
        url: API_BASE_URL + "/user/checkEmailAvailability?email=" + email,
        method: 'GET'
    });
}


export function getCurrentUser() {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/user/me",
        method: 'GET'
    });
}

export function getUserProfile(apogeecode) {
    return request({
        url: API_BASE_URL + "/users/" + apogeecode,
        method: 'GET'
    });
}

export function getUserCreatedElections(apogeecode, page, size) {
    page = page || 0;
    size = size || ELECTION_LIST_SIZE;

    return request({
        url: API_BASE_URL + "/users/" + apogeecode + "/elections?page=" + page + "&size=" + size,
        method: 'GET'
    });
}

export function getUserVotedElections(apogeecode, page, size) {
    page = page || 0;
    size = size || ELECTION_LIST_SIZE;

    return request({
        url: API_BASE_URL + "/users/" + apogeecode + "/votes?page=" + page + "&size=" + size,
        method: 'GET'
    });
}
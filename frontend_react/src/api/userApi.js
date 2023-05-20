// userApi.js
import axios from 'axios';

const API_URL = 'http://localhost:8080'; // change to your Spring Boot app's URL

export function login(email, password) {
    return axios.post(`${API_URL}/signin`, {
        email: email,
        password: password
    });
}

export function register(userDetails) {
    return axios.post(`${API_URL}/signup`, userDetails);
}

// userApi.js
import axios from 'axios';

const API_URL = "http://localhost:8080/api/auth/"; // change to your Spring Boot app's URL

export function login(email, password) {
    return axios.post(`${API_URL}/signin`, {
        email: email,
        password: password
    });
}

export function register(name, emailAdress, password,apogeeCode,role) {
    return axios.post(API_URL + "signup", {
        name,
        emailAdress,
        password,
        apogeeCode,
        role
      });
}

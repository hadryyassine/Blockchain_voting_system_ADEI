// userApi.js
import axios from 'axios';

const API_URL = "http://localhost:8080/api/auth"; // change to your Spring Boot app's URL

export function login(emailAdress, password) {
    return axios
      .post(API_URL + "/signin", {
        emailAdress,
        password
      })
      .then(response => {
        if (response.data.accessToken) {
          localStorage.setItem("user", JSON.stringify(response.data));
        }
        return response.data;
      });
}

export function register(name, emailAdress, password,apogeeCode,roles) {
    return axios.post(API_URL + "/signup", {
        name,
        emailAdress,
        password,
        apogeeCode,
        role: Array.isArray(roles) ? roles : [roles]
      });
}

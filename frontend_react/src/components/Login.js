import { useState } from 'react';
import { loginFields } from "../constants/formFields";
import FormAction from "./FormAction";
import {Input} from "./Input";
import { login } from '../api/userApi';


const fields=loginFields;
let fieldsState = {};
fields.forEach(field=>fieldsState[field.id]='');

export default function Login(){
    const [loginState,setLoginState]=useState(fieldsState);

    const handleChange=(e)=>{
        setLoginState({...loginState,[e.target.id]:e.target.value})
    }

    const handleSubmit=(e)=>{
        e.preventDefault();
        authenticateUser();
    }

    //Handle Login API Integration here
    const authenticateUser = () => {
        console.log('Form submission started');
        console.log('Email:', loginState.emailAdress);
        console.log('Password:', loginState.password);

        
        login(loginState.emailAdress, loginState.password)
        .then(response => {
            console.log('Login API response received:', response);
            console.log(response.data.id);

            // handle successful login
            window.location.href = "https://www.ieee.org";
        })
        .catch(error => {
            // Improved error handling
            console.log('Error occurred during login:', error);
            if (error.response) {
                console.log('Error status', error.response.status);
                console.log('Error details', error.response.data);
            } else if (error.request) {
                console.log('No response was received', error.request);
            } else {
                console.log('Error', error.message);
            }
            console.log('Error config', error.config);
        });
    
        console.log('Form submission ended');
    }
    

    return(
        <form className="mt-8 space-y-6" onSubmit={handleSubmit}>
        <div className="-space-y-px">
            {
                fields.map(field=>
                        <Input
                            key={field.id}
                            handleChange={handleChange}
                            value={loginState[field.id]}
                            labelText={field.labelText}
                            labelFor={field.labelFor}
                            id={field.id}
                            name={field.name}
                            type={field.type}
                            isRequired={field.isRequired}
                            placeholder={field.placeholder}
                    />
                
                )
            }
        </div>

        <FormAction handleSubmit={handleSubmit} text="Login"/>

      </form>
    )
}


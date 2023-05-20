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
    const authenticateUser = () =>{

        login(loginState.email, loginState.password)
        .then(response => {
            console.log(response);
            // handle successful login
        })
        .catch(error => {
            console.log(error);
            // handle error
        });

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


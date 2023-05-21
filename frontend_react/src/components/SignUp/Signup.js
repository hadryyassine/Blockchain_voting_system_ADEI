import { useState } from 'react';
import { signupFields } from "../../constants/formFields"
import FormAction from "../FormAction";
import { Input } from "../Input";
import { register } from '../../api/userApi';
import "./Signup.css"

const fields = signupFields;
let fieldsState = {};

fields.forEach(field => fieldsState[field.id] = '');

export default function Signup() {
    const [signupState, setSignupState] = useState(fieldsState);

    const handleChange = (e) => setSignupState({ ...signupState, [e.target.id]: e.target.value });

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log(signupState)
        createAccount()
    }

    //handle Signup API Integration here
    const createAccount = () => {
        register(signupState)
            .then(response => {
                console.log(response);
                // handle successful registration
                window.location.href = "https://www.google.com";

            })
            .catch(error => {
                console.log(error);
                // handle error
            });

    }

    return (
        <div className="min-h-full h-screen flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
            <div className="max-w-md w-full space-y-8">
                <form className="mt-8 space-y-6" onSubmit={handleSubmit}>
                    <div className="">
                        {
                            fields.map(field => {
                                if (field.type === 'select') {
                                    return (
                                        <div key={field.id}>
                                            <label htmlFor={field.id}>{field.labelText}</label>
                                            <select
                                                id={field.id}
                                                name={field.name}
                                                required={field.isRequired}
                                                value={signupState[field.id]}
                                                onChange={handleChange}
                                            >
                                                <option value="">--Please choose an option--</option>
                                                {field.options.map(option => (
                                                    <option key={option} value={option}>
                                                        {option}
                                                    </option>
                                                ))}
                                            </select>
                                        </div>
                                    );
                                } else {
                                    return (
                                        <Input
                                            key={field.id}
                                            handleChange={handleChange}
                                            value={signupState[field.id]}
                                            labelText={field.labelText}
                                            labelFor={field.labelFor}
                                            id={field.id}
                                            name={field.name}
                                            type={field.type}
                                            isRequired={field.isRequired}
                                            placeholder={field.placeholder}
                                        />
                                    );
                                }
                            })
                        }
                        <FormAction handleSubmit={handleSubmit} text="Signup" />
                    </div>



                </form>

            </div>
        </div>
    )
}
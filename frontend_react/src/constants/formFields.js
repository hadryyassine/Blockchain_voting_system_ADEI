const loginFields=[
    {
        labelText:"Email address",
        labelFor:"email-address",
        id:"email-address",
        name:"email",
        type:"email",
        autoComplete:"email",
        isRequired:true,
        placeholder:"Email address"   
    },
    {
        labelText:"Password",
        labelFor:"password",
        id:"password",
        name:"password",
        type:"password",
        autoComplete:"current-password",
        isRequired:true,
        placeholder:"Password"   
    }
]

const signupFields=[
    {
        labelText:"Full Name",
        labelFor:"name",
        id:"name",
        name:"name",
        type:"text",
        autoComplete:"name",
        isRequired:true,
        placeholder:"Full Name"   
    },
    {
        labelText:"Email address",
        labelFor:"emailAddress",
        id:"emailAddress",
        name:"emailAddress",
        type:"email",
        autoComplete:"emailAddress",
        isRequired:true,
        placeholder:"Email address"   
    },
    {
        labelText:"Password",
        labelFor:"password",
        id:"password",
        name:"password",
        type:"password",
        autoComplete:"current-password",
        isRequired:true,
        placeholder:"Password"   
    },

    {
        labelText:"Code apogee",
        labelFor:"apogeeCode",
        id:"apogeeCode",
        name:"apogeeCode",
        type:"apogeeCode",
        autoComplete:"apogeeCode",
        isRequired:true,
        placeholder:"Code apogee"   
    },

    {
        labelText:"Role",
        labelFor:"role",
        id:"role",
        name:"role",
        type:"select",
        options: ['voter', 'candidate'],
        isRequired:true,
        placeholder:"role"   
    }

    

]

export {loginFields,signupFields}
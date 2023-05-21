const loginFields=[
    {
        labelText:"Email address",
        labelFor:"emailAdress",
        id:"emailAdress",
        name:"emailAdress",
        type:"emailAdress",
        autoComplete:"emailAdress",
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
        labelFor:"roles",
        id:"roles",
        name:"roles",
        type:"select",
        options: ['VOTER', 'CANDIDATE'],
        isRequired:true,
        placeholder:"roles"   
    }

    

]

export {loginFields,signupFields}
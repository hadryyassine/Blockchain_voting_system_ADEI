// ***********************************************
// This example commands.js shows you how to
// create various custom commands and overwrite
// existing commands.
//
// For more comprehensive examples of custom
// commands please read more here:
// https://on.cypress.io/custom-commands
// ***********************************************
//
//
// -- This is a parent command --
// Cypress.Commands.add('login', (email, password) => { ... })
//
//
// -- This is a child command --
// Cypress.Commands.add('drag', { prevSubject: 'element'}, (subject, options) => { ... })
//
//
// -- This is a dual command --
// Cypress.Commands.add('dismiss', { prevSubject: 'optional'}, (subject, options) => { ... })
//
//
// -- This will overwrite an existing command --
// Cypress.Commands.overwrite('visit', (originalFn, url, options) => { ... })

Cypress.Commands.add('login', (apogeecodeOrEmail, password) => {
    cy.request({
        method: 'POST',
        url: 'http://localhost:3000/auth/signin', // or whatever the correct URL is
        body: {
            apogeecodeOrEmail: apogeecodeOrEmail,
            password: password,
        },
    }).then(resp => {
        window.localStorage.setItem('accessToken', resp.body.accessToken);
    });
  });
  
  Cypress.Commands.add('signup', (name,apogeecode,email,password) => {
    cy.request({
      method: 'POST',
      url: 'http://local/auth/signup',
      body: {
        name: name,
        apogeecode: apogeecode,
        email: email,
        password: password,
      },
    }).then(resp => {
      window.localStorage.setItem('accessToken', resp.body.accessToken);
    });
  });


  Cypress.Commands.add('logout', () => {
    window.localStorage.removeItem('accessToken');
  });
  
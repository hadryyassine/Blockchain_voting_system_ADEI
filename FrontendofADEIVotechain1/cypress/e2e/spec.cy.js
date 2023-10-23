describe('ENSIAS ADEI Votechain Application', () => {
  beforeEach(() => {
    cy.logout();
  });

  it('loads the homepage', () => {
    cy.visit('http://localhost:3000');
    cy.contains('ENSIAS ADEI Votechain');
  });

  it('signs up a new user', () => {
    cy.visit('http://localhost:3000/signup');
    cy.get('input[name="name"]').type('yassinos');
    cy.get('input[name="apogeecode"]').type('926777');
    cy.get('input[name="email"]').type('testuser187@example.com');
    cy.get('input[name="password"]').type('testpassword');
    cy.get('.signup-form-button').click();
  });

  it('logs in an existing user', () => {
    cy.visit('http://localhost:3000/login');
    cy.get('input[name="apogeecodeOrEmail"]').type('926777');
    cy.get('input[name="password"]').type('testpassword');
    cy.get('.login-form-button').click();
  });

  it('creates a new poll', () => {

    cy.visit('http://localhost:3000/login');
    cy.get('input[name="apogeecodeOrEmail"]').type('inssaf@gmail.com');
    cy.get('input[name="password"]').type('123456789');
    cy.get('.login-form-button').click();
    cy.visit('http://localhost:3000/poll/new');
    cy.get('textarea[name="positiontitle"]').type('Test position?');
    cy.get('input[name^="candidates"]').first().type('Candidate 1');
    cy.get('input[name^="candidates"]').last().type('Candidate 2');
    cy.get('.create-election-form-button').click();
  });

  it('votes in a poll', () => {
    cy.visit('http://localhost:3000/login');
    cy.get('input[name="apogeecodeOrEmail"]').type('inssaf@gmail.com');
    cy.get('input[name="password"]').type('123456789');
    cy.get('.login-form-button').click();
    cy.visit('http://localhost:3000/');
    cy.get('.poll-vote-input').first().click();
    cy.get('.vote-button').click();
  });

  it('views poll results', () => {
    cy.visit('http://localhost:3000/login');
    cy.get('input[name="apogeecodeOrEmail"]').type('inssaf@gmail.com');
    cy.get('input[name="password"]').type('123456789');
    cy.get('.login-form-button').click();
    cy.visit('http://localhost:3000/');
    cy.get('.poll-link').first().click();
    cy.get('.poll-results').should('exist');
  });
});

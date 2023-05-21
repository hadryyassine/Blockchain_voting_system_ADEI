import React, { useState } from 'react';
import userImage from '../../images/user.png';

function Sidebar() {
  const [activePage, setActivePage] = useState('dashboard');
  const [candidatesOpen, setCandidatesOpen] = useState(false);

  const handlePageClick = (page) => {
    setActivePage(page);
  };

  const toggleCandidates = () => {
    setCandidatesOpen(!candidatesOpen);
  };

  return (
    <div className="d-flex flex-column  text-white bg-dark col-md-2" style={{ height: '100vh' }}>
      <a href="/" className="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-white text-decoration-none">
        <svg className="bi me-2" width="40" height="32"><use xlinkHref="#bootstrap"></use></svg>
        <span className="fs-4">ADEI Elections</span>
      </a>
      <hr />
      <ul className="nav nav-pills flex-column mb-auto">
        <li className="nav-item">
          <a
            href="#"
            className={`nav-link ${activePage === 'dashboard' ? 'active' : ''}`}
            onClick={() => handlePageClick('dashboard')}
          >
            <svg className="bi me-2" width="16" height="16"><use xlinkHref="#home"></use></svg>
            Dashboard
          </a>
        </li>
        <li>
          <a
            href="#"
            className={`nav-link ${activePage === 'candidates' ? 'active' : ''}`}
            onClick={() => {
              handlePageClick('candidates');
              toggleCandidates();
            }}
          >
            <svg className="bi me-2" width="16" height="16"><use xlinkHref="#speedometer2"></use></svg>
            Candidates
          </a>
          {candidatesOpen && (
            <div className="collapse show" id="candidates-collapse">
              <ul className="btn-toggle-nav list-unstyled fw-normal pb-1 small">
                <li><a href="#" className="link-light rounded"> pres</a></li>
                <li><a href="#" className="link-light rounded"> vice pres</a></li>
                <li><a href="#" className="link-light rounded"> tres</a></li>
              </ul>
            </div>
          )}
        </li>
        <li>
          <a
            href="#"
            className={`nav-link ${activePage === 'vote' ? 'active' : ''}`}
            onClick={() => handlePageClick('vote')}
          >
            <svg className="bi me-2" width="16" height="16"><use xlinkHref="#table"></use></svg>
            Vote
          </a>
        </li>
        <li>
          <a
            href="#"
            className={`nav-link ${activePage === 'hallOfFame' ? 'active' : ''}`}
            onClick={() => handlePageClick('hallOfFame')}
          >
            <svg className="bi me-2" width="16" height="16"><use xlinkHref="#grid"></use></svg>
            Hall of Fame
          </a>
        </li>
      </ul>
      <hr />
      <div className="dropdown">
        <a href="#" className="d-flex align-items-center text-white text-decoration-none dropdown-toggle" id="dropdownUser1" data-bs-toggle="dropdown" aria-expanded="false">
          <img src={userImage} alt="" width="32" height="32" className="rounded-circle me-2" />
          <strong>User</strong>
        </a>
        <ul className="dropdown-menu dropdown-menu-dark text-small shadow" aria-labelledby="dropdownUser1">
          <li><a className="dropdown-item" href="#">Vote for your Candidat...</a></li>
          <li><a className="dropdown-item" href="#">Settings</a></li>
          <li><a className="dropdown-item" href="#">Profile</a></li>
          <li><hr className="dropdown-divider" /></li>
          <li><a className="dropdown-item" href="#">Sign out</a></li>
        </ul>
      </div>
    </div>
  );
}

export default Sidebar;

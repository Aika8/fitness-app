import React from 'react';
import banner from './banner.png';
import './navbar.css';
const Navbar = () => {
    return (
        <header>
        <nav className="navbar navbar-expand-lg navbar-dark">
        <div className="container-fluid">
          <a className="navbar-brand" href="#"> Maria Plume</a>
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav">
              <li className="nav-item">
                <a className="nav-link active" aria-current="page" href="#">Home</a>
              </li>
              <li className="nav-item">
                <a className="nav-link" href="#">About</a>
              </li>
              <li className="nav-item">
                <a className="nav-link" href="#">Posts</a>
              </li>
              <li className="nav-item">
                <a className="nav-link">Login</a>
              </li>
            </ul>
          </div>
        </div>
      </nav>
      <div className="header-text" style={{ backgroundImage: `url(${banner})` }}>
          <div className="header-text-block">
                <h2>Ваш личный тренер</h2>
                <p>Стретчинг.  Фитнес.  Йога.</p>
                <button>Скажи мне больше</button>
            </div>
      </div>
      </header> 
    )
};

export default Navbar;
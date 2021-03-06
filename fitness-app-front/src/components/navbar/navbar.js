import React, {useEffect} from 'react';
import banner from './banner.png';
import './navbar.css';
import { Link, NavLink } from 'react-router-dom';
const Navbar = (props) => {
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
                <a className="nav-link active" aria-current="page" href="#">Главная</a>
              </li>
              {
                props.currentUser.role.name==="ROLE_ADMIN"?(
                <li className="nav-item">
                    <NavLink to="/admin" className="nav-link">Админка</NavLink>
                </li>):""
              }
              <li className="nav-item">
                <a className="nav-link" href="#posts">Посты</a>
              </li>
                { props.authenticated ? (
                        <li className="nav-item">
                            <a className="nav-link" onClick={props.onLogout}>Выйти</a>
                        </li>
                ): (
                        <li className="nav-item">
                            <NavLink to="/login" className="nav-link">Зайти</NavLink>
                        </li>
                )}
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
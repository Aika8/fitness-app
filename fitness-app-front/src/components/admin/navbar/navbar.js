import React from 'react';
import { NavLink } from "react-router-dom";

const Navbar = () => {
    return (
        <ul className="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

        <NavLink to="/admin" className="sidebar-brand d-flex align-items-center justify-content-center">

            <div className="sidebar-brand-icon rotate-n-15">
                <i className="fas fa-laugh-wink"></i>
            </div>
            <div className="sidebar-brand-text mx-3"> Fitness-Admin</div>
        </NavLink>
        
        <hr className="sidebar-divider my-0"/>

        <hr className="sidebar-divider"/>

   
        <div className="sidebar-heading">
            Addons
        </div>

        <li className="nav-item">
            <a className="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePages"
                aria-expanded="true" aria-controls="collapsePages">
                <i className="fas fa-fw fa-folder"></i>
                <span>Страницы</span>
            </a>
            <div id="collapsePages" className="collapse" aria-labelledby="headingPages" data-parent="#accordionSidebar">
                <div className="bg-white py-2 collapse-inner rounded">
                    <h6 className="collapse-header">Login Screens:</h6>
                    <NavLink to="/admin/posts" className="collapse-item">Посты</NavLink>
                    <NavLink to="/admin/users" className="collapse-item">Пользователи</NavLink>
                    <NavLink to="/admin/roles" className="collapse-item">Роли</NavLink>
                    <div className="collapse-divider"></div>
                    <h6 className="collapse-header">Другие страницы</h6>
                    <NavLink to="/" className="collapse-item">Главная</NavLink>
                    <a className="collapse-item" href="404.html">404 Ошибка</a>
                </div>
            </div>
        </li>

        
        <li className="nav-item">
            <a className="nav-link" href="charts.html">
                <i className="fas fa-fw fa-chart-area"></i>
                <span>Charts</span></a>
        </li>
        <hr className="sidebar-divider d-none d-md-block"/>

      
        <div className="text-center d-none d-md-inline">
            <button className="rounded-circle border-0" id="sidebarToggle"></button>
        </div>

      

    </ul>
    )
}

export default Navbar;
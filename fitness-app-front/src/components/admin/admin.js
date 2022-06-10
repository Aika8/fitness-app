import React, {useEffect} from "react";
import Navbar from "./navbar";
import Nav from './nav';
import { useNavigate  } from 'react-router-dom'

const Admin = ({currentUser}) => {
    const navigate = useNavigate();

    useEffect(()=>{
        if(currentUser.role.name === "ROLE_USER") {
            return navigate('/');            
        }
    });

    return (


        <div id="wrapper">

        <Navbar />
        <div id="content-wrapper" class="d-flex flex-column">

            <div id="content">
                <Nav />
                <div className="container">
                    <h1>Привет!!</h1>               
                </div>

            </div>
        </div>

    </div>
    )
}

export default Admin;
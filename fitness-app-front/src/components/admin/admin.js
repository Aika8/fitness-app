import React from "react";
import Navbar from "./navbar";
import Nav from './nav';

import FroalaEditor from "./FroalaEditor.js";

const Admin = () => {
    return (
        <div id="wrapper">

        <Navbar/>
        <div id="content-wrapper" class="d-flex flex-column">

            <div id="content">
                <Nav/>
                <FroalaEditor/>
            <div/>
        </div>
        </div>
        </div>
    )
}

export default Admin;
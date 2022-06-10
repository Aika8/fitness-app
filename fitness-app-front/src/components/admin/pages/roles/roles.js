import React, {useEffect, useState} from "react";

import { getAllRoles } from "../../../../service/service";
import Navbar from "../../navbar";
import Nav from '../../nav';
import { NavLink } from "react-router-dom";

const AdminRoles = () => {

    const [roles, setRoles] = useState([]);
    useEffect(() => {
        getAllRoles(localStorage.getItem("accessToken")).then(res=>{
          setRoles(res.data);
        });
      }, []);

    const deleteRole = (id) => {
        setRoles(roles.filter(e => e.id !== id));
    }
    

    return (
        <div id="wrapper">

        <Navbar />
        <div id="content-wrapper" className="d-flex flex-column">

            <div id="content">
                <Nav />
                <div className="container">

                    <NavLink to="/admin/post/add" className="btn btn-secondary mb-2">Add new role</NavLink>
                    
               
            <table className="table table-bordered" id="dataTable" width="100%" cellSpacing="0">
                <thead>
                    <tr>
                        <th scope="col" >#</th>
                        <th scope="col" className="w-75">Name</th>
                        <th scope="col" >Options</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        roles.map(e=>{
                            return ( 
                                <tr key={e.id} scope="row">
                                    <td>{e.id}</td>
                                    <td>{e.name}</td>
                                    <td>
                                        <NavLink to={`/admin/post/edit/${e.id}`} className="btn btn-primary" style={{marginRight:"8px"}}>Update</NavLink>
                                        <span className="btn btn-warning"  
                                        name = {e.id}
                                        onClick={()=>deleteRole(e.id)}>Delete</span></td>
                                </tr>
                            )
                        })
                    }
                </tbody>
                </table>
                </div>
             
                </div>
                  
                    </div>                
                </div>
    )
}

export default AdminRoles;
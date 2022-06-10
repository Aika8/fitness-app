import React, {useEffect, useState} from "react";
import { getAllUsers, saveUser } from "../../../../service/service";
import Navbar from "../../navbar";
import Nav from '../../nav';
const AdminUsers = () => {

    const [users, setUsers] = useState([]);
    const [current, setCurrent] = useState(1);
    const [size, setSize] = useState(0);

    useEffect(()=>{
        getAllUsers(current, localStorage.getItem("accessToken")).then(res=>{
            setUsers(res.data.content);
            setSize(res.data.totalPages);
            setCurrent(res.data.number);
          });
    },[]);

    const deleteUser = (id) => {
        setUsers(users.filter(e => e.id !== id));
    }
    
    const newPage = (page) => {
        getAllUsers(page).then(res=>{
            setUsers(res.data.content);
            setCurrent(res.data.number);
          });
    }

    const handleForm = (e) => {
        e.preventDefault();

        const {id, email, name, image} = e.target;
        
        saveUser({id:id.value, email:email.value, name:name.value, imageUrl: image.value}, localStorage.getItem("accessToken"));
        
    }
    return (
        <div id="wrapper">

        <Navbar />
        <div id="content-wrapper" class="d-flex flex-column">

            <div id="content">
                <Nav />
                <div className="container">
                <div>
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th scope="col" >#</th>
                        <th scope="col" className="w-50">Email</th>
                        <th scope="col">Role</th>
                        <th scope="col" >Options</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        users.map(e=>{
                            return ( 
                                <tr key={e.id} scope="row">
                                    <td>{e.id}</td>
                                    <td>{e.email}</td>
                                    <td>{e.role.authority}</td>
                                    <td>
                                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target={`#modal${e.id}`}  style={{marginRight:"8px"}}>Update</button>
                                        <span className="btn btn-warning"  
                                        name = {e.id}
                                        onClick={()=>deleteUser(e.id)}>Delete</span>
                                    
                                         <div class="modal fade" id={`modal${e.id}`} tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                        <div class="modal-dialog" role="document">
                                            <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel">Modal title</h5>
                                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                            <form onSubmit={handleForm}>
                                            <input type="text"  class="form-control" id="id" value={e.id} hidden="true"/>

                                            <div class="form-group">
                                                <label for="email">Email address</label>
                                                <input type="email" id="email" class="form-control"  aria-describedby="emailHelp" placeholder="Enter email" value={e.email} readOnly/>
                                            </div>
                                            <div class="form-group">
                                                <label for="image">Image</label>
                                                <input type="text" id="image" class="form-control"  placeholder="image" defaultValue = {e.imageUrl}
                                            />
                                            </div>
                                            <div class="form-group">
                                                <label for="name">Name</label>
                                                <input type="text" id="name" class="form-control" placeholder="name" defaultValue={e.name} />
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                                <button type="submit" class="btn btn-primary">Save changes</button>
                                            </div>
                                            </form>
                                            </div>
                                            
                                            </div>
                                        </div>
                                        </div>
                                        </td>
                                </tr>
                            )
                        })
                    }
                </tbody>
                </table>
                    <nav aria-label="Page navigation ">
                        <ul class="pagination">
                            <li class="page-item"><a class="page-link" onClick={()=>newPage(current-1)}>Previous</a></li>
                            {
                                Array.from(Array(size).keys()).map (e => <li class="page-item">
                                    <a class="page-link" onClick={()=>newPage(e+1)}>{e+1}</a>
                                    </li> 
                                    )
                            }
                            <li class="page-item"><a class="page-link"  onClick={()=>newPage(current+1)}>Next</a></li>
                        </ul>
                    </nav>
                       
                    </div>                
                </div>
            </div>
        </div>

    </div>
    );
}

export default AdminUsers;
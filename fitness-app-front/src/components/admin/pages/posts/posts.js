import React, {useState, useEffect} from "react";
import { getAllPosts , deletePost} from "../../../../service/service";
import Navbar from "../../navbar";
import Nav from '../../nav';
import { NavLink } from "react-router-dom";

const Posts = () => {
    const [posts, setPosts] = useState([]);
    const [size, setSize] = useState(0);
    const [current, setCurrent] = useState(1);
    useEffect(() => {
        getAllPosts(current).then(res=>{
          setPosts(res.data.content);
          setSize(res.data.totalPages);
          setCurrent(res.data.number);
        });
      }, []);

    const deletePostFromTable = (id) => {
        setPosts(posts.filter(e => e.id !== id));
        deletePost(id, localStorage.getItem('accessToken'));
    }   
    
    const newPage = (page) => {
        getAllPosts(page).then(res=>{
            setPosts(res.data.content);
            setCurrent(res.data.number);
            setSize(res.data.totalPages);
          });
    }


    return (

        <div id="wrapper">

        <Navbar />
        <div id="content-wrapper" class="d-flex flex-column">

            <div id="content">
                <Nav />
                <div className="container">

                    <NavLink to="/admin/post/add" className="btn btn-secondary mb-2">Add new posts</NavLink>
                    
               
            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                <thead>
                    <tr>
                        <th scope="col" >#</th>
                        <th scope="col" className="w-75">Title</th>
                        <th scope="col" >Options</th>
                    </tr>
                </thead>
                <tbody>
                    {
                        posts.map(e=>{
                            return ( 
                                <tr key={e.id} scope="row">
                                    <td>{e.id}</td>
                                    <td>{e.title}</td>
                                    <td>
                                        <NavLink to={`/admin/post/edit/${e.id}`} className="btn btn-primary" style={{marginRight:"8px"}}>Update</NavLink>
                                        <span className="btn btn-warning"  
                                        name = {e.id}
                                        onClick={()=>deletePostFromTable(e.id)}>Delete</span></td>
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

       
    )
}

export default Posts;
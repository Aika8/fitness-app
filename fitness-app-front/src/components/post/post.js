import React, {useEffect, useState } from "react";
import './post.css';
import {getPost} from "../../service/service";
import {
    useParams
  } from "react-router-dom";
const Post = () => {
    const { id } = useParams();
    const [post, setPost] = useState({});

    useEffect(()=>{
        getPost(id).then(res=>{
            console.log(res.data);
            setPost(res.data);
        }
            )
    }, [id])
    return (
        <div className="container">
            <div className="post">
                 <h2 className="">{post.title}</h2>
                 <div dangerouslySetInnerHTML={{ __html: post.description }}>
                 </div>
            </div>
        </div>
    )
}

export default Post;
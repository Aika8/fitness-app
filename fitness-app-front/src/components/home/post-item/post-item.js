import React from "react";
import { useNavigate } from "react-router-dom";

const PostItem = ({post}) => {
    const navigate = useNavigate();
    const {id, cover, title, brief} = post;

    return (
            <div className="card" onClick={()=>navigate(`/post/${id}`)} >
                <img className="card-img-top" src={cover} alt="Card image cap" />
                <div className="card-body">
                    <h5 className="card-title">{title}</h5>
                    <p className="card-text">{brief}</p>
                </div>
                </div>
    )
}

export default PostItem;
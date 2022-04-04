import React from "react";

const PostItem = ({post}) => {
    console.log(post);
    const {cover, title, brief} = post;
    return (
            <div className="card">
            <img className="card-img-top" src={cover} alt="Card image cap"/>
            <div className="card-body">
                <h5 className="card-title">{title}</h5>
                <p className="card-text">{brief}</p>
            </div>
            </div>
    )
}

export default PostItem;
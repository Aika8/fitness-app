import React, {useEffect, useState } from "react";
import './post.css';
import {getPost, addComment} from "../../service/service";
import {
    useParams
  } from "react-router-dom";
const Post = () => {
    const { id } = useParams();
    const [post, setPost] = useState({});
    const [comments, setComments] = useState([]);
    const [likes, setLikes] = useState([]);
    const [text, setText] = useState("");
    useEffect(()=>{
        getPost(id).then(res=>{
            setPost(res.data);
            setComments(res.data.comments);
            setLikes(res.data.likes);
        }
            )
    }, [id]);


    const handleComment= e => {
        e.preventDefault();
        const comment = {
            postId: post.id,
            message: text
        }
        addComment(comment, localStorage.getItem("accessToken"))
            .then(res=> {
                if(res.status === 200) {
                    setComments([...comments, res.data]);
                }
                setText("");
            }
            );
    }

    return (
        <section className="main_post">
            <nav className="main_post_nav">
            </nav>
            <div className="container main_post_div">
                <div className="post">
                    <div className="d-flex justify-content-between">
                        <h1>{post.title}</h1>
                        <button className="btn btn-success btn-sm">Like</button>
                    </div>
                    <div className="line"></div>
                    <div dangerouslySetInnerHTML={{ __html: post.description }}>
                    </div>
                </div>
            </div>
            <div className="main_post_comments">
                <div className="responses">
                    <h4>Комментарии({comments.length})</h4>
                    <form onSubmit={handleComment}>
                        <input name="comment" type="text" placeholder="Что думаете?" class="form-control" 
                    value={text} onChange={(e)=>setText(e.target.value)} />
                    </form>
                </div>
                <div className="line"></div>
                <div className="main_comments overflow-auto" style={{maxHeight:"100vh"}}> 
                    {
                        comments.map(e=>{
                            return(
                            <div key={e.id}>
                                <div className="d-flex mt-2 mb-2">
                                    <img src={e.userImg} tag="profile"/>
                                    <div style={{marginLeft:"10px", marginTop:"-5px"}}>
                                        <p>{e.userEmail}</p>
                                        <p>{e.creationDate}</p>
                                    </div>
                                </div>
                                <p>{e.message}</p>
                            <div className="line"></div>
                            </div>
                            )
                        })
                    }
                    
                </div>
            </div>
        </section>
    )
}

export default Post;
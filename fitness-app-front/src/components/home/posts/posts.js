import React, { useRef, useEffect, useState } from "react";
import {getAllPosts} from '../../../service/service';
import PostItem from "../post-item";
import "./posts.css"; 

const Posts = () => {
    const [posts, setPosts] = useState([]);
    useEffect(() => {
        getAllPosts().then(res=>{
          setPosts(res.data.content);
        });
      },  []);
    return (
    <section className="home-posts pb-5" id="posts">
        <h1>Посты</h1>
       <div className="home-block row">
          {
            posts.map(el => {
              return (
              <div className="col-md-4 col-sm-12 mt-2" key={el.id}>
                  <PostItem post = {el}/>
              </div>)
            }
            )
          }
       </div>
    </section>
    )
}

export default Posts;
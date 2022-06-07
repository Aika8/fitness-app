import axios from 'axios';
import { API_BASE_URL} from '../components/constants';


// const axiosInstanse  = axios.create({
//   baseURL: "https://fitness-rest-api-back.herokuapp.com"
// });


const axiosInstanse  = axios.create({
  baseURL: API_BASE_URL
});


export const getAllPosts = async(page) => {
  return await axiosInstanse.post(`/api/post/filter?page=${page-1}&sort=id,desc`);
}

export const getPost = async(id) => {
  return await axiosInstanse.get(`/api/post/${id}`);
}

export const addPost = async(post, ACCESS_TOKEN) => {
  return await axiosInstanse.post(`/api/post`, post, {
    headers: {
      Authorization: 'Bearer ' + ACCESS_TOKEN
    }
   });
}

export const addComment = async(comment, ACCESS_TOKEN) => {

  return await axiosInstanse.post(`/api/comment/`, comment, {
    headers: {
      Authorization: 'Bearer ' + ACCESS_TOKEN
    }
   });
}

// class EService{


//   async getAllPosts() {
//     return await axiosInstanse.get(`/api/posts`);
//   }

//   async getPost(id) {
//     return await axiosInstanse.get(`/api/post?id=${id}`);;
//   }

//   async addPost(post) {
//     return await axiosInstanse.post(`/api/post`, post);
//   }

// }

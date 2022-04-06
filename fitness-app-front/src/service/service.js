import axios from 'axios';


// const axiosInstanse  = axios.create({
//   baseURL: "https://fitness-rest-api-back.herokuapp.com"
// });


const axiosInstanse  = axios.create({
  baseURL: "http://20.112.8.98:6379"
});

// const axiosInstanse  = axios.create({
//   baseURL: "http://app-server"
// });


export const getAllPosts = async() => {
  return await axiosInstanse.post(`/api/post/filter`);
}

export const getPost = async(id) => {
  return await axiosInstanse.get(`/api/post/${id}`);
}

export const addPost = async(post) => {
  return await axiosInstanse.post(`/api/post`, post);
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

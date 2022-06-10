import axios from 'axios';


// const axiosInstanse  = axios.create({
//   baseURL: "https://fitness-rest-api-back.herokuapp.com"
// });


const axiosInstanse  = axios.create({
  // baseURL: "http://51.116.236.19:8080"
  baseURL: "http://localhost:8080"
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

export const deletePost = async(id, ACCESS_TOKEN) => {
  return await axiosInstanse.delete(`/api/post/${id}`, {
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

export const getAllUsers = async(page, ACCESS_TOKEN) => {

  return await axiosInstanse.get(`/api/user/all?page=${page-1}&sort=id,desc`, {
    headers: {
      Authorization: 'Bearer ' + ACCESS_TOKEN
    }
   });
}

export const getAllRoles = async(ACCESS_TOKEN) => {

  return await axiosInstanse.get(`/api/role/all`, {
    headers: {
      Authorization: 'Bearer ' + ACCESS_TOKEN
    }
   });
}

export const saveUser = async(user, ACCESS_TOKEN) => {

  return await axiosInstanse.post(`/api/user`, user ,{
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

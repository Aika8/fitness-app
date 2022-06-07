import React, { Component } from 'react';
import Home from '../home';
import Post from '../post';
import Admin from '../admin';
import AdminPosts from '../admin/pages/posts';
import AddPost from '../admin/pages/posts/add';
import EditPost from '../admin/pages/posts/edit';

import Login from '../user/login/Login';
import Signup from '../user/signup/Signup';
import Profile from '../user/profile/Profile';
import OAuth2RedirectHandler from '../user/oauth2/OAuth2RedirectHandler';
import NotFound from '../common/NotFound';
import LoadingIndicator from '../common/LoadingIndicator';
import { getCurrentUser } from '../util/APIUtils';
import { ACCESS_TOKEN } from '../constants';
import Alert from 'react-s-alert';
import {
    BrowserRouter as Router,
    Routes ,
    Route,
    Link
} from "react-router-dom";
import Posts from '../home/posts';

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            authenticated: false,
            currentUser: {
                role : {
                    name:"ROLE_USER"
                }
            },
            loading: true
        }

        this.loadCurrentlyLoggedInUser = this.loadCurrentlyLoggedInUser.bind(this);
        this.handleLogout = this.handleLogout.bind(this);
        this.setAuth = this.setAuth.bind(this);
    }

    setAuth(){
        this.loadCurrentlyLoggedInUser();
    }

    loadCurrentlyLoggedInUser() {
        getCurrentUser()
            .then(response => {
                this.setState({
                    currentUser: response,
                    authenticated: true,
                    loading: false
                });
            }).catch(error => {
            this.setState({
                loading: false
            });
        });
    }

    handleLogout() {
        localStorage.removeItem(ACCESS_TOKEN);
        this.setState({
            authenticated: false,
            currentUser: {
                role : {
                    name:"ROLE_USER"
                }
            }
        });
        Alert.success("You're safely logged out!");
    }

   
    componentDidMount() {
        this.loadCurrentlyLoggedInUser();
    }

    render() {

        // guess we need better loader
        if(this.state.loading) {
            return <LoadingIndicator />
        }

        // In Admin Component it seems we need a check, if not the admin then to the main page
        return (
            <Router>
                    <Routes>
                        <Route exact path="/" 
                            element={<Home authenticated={this.state.authenticated} currentUser={this.state.currentUser} onLogout={this.handleLogout}/>}/>
                        <Route exact path="/post/:id" element ={<Post />}/>

                        <Route path="/admin"  element={<Admin currentUser={this.state.currentUser}/>}/>
                        <Route exact path="/admin/posts/"  element={<AdminPosts/>}/>
                        <Route path="/admin/post/add"  element={<AddPost/>}/>
                        <Route path="/admin/post/edit/:id"  element={<EditPost/>}/>

                        <Route path="/profile" 
                            element={<Profile authenticated={this.state.authenticated} currentUser={this.state.currentUser} />}/>

                        <Route path="/login" 
                               element={<Login authenticated={this.state.authenticated} setAuth={this.setAuth}/>}/>

                        <Route path="/signup"
                               element = {<Signup authenticated={this.state.authenticated} />}/>
                        <Route path="/oauth2/redirect" element={<OAuth2RedirectHandler setAuth={this.setAuth} />}/>
                        <Route path='*' element={<NotFound />} />
                    </Routes >
            </Router>
        );
    }
}

export default App;
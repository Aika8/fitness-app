import React, { Component } from 'react';
import Home from '../home';
import Post from '../post';
import Admin from '../admin';
import Login from '../user/login/Login';
import Signup from '../user/signup/Signup';
import Profile from '../user/profile/Profile';
import OAuth2RedirectHandler from '../user/oauth2/OAuth2RedirectHandler';
import NotFound from '../common/NotFound';
import LoadingIndicator from '../common/LoadingIndicator';
import { getCurrentUser } from '../util/APIUtils';
import { ACCESS_TOKEN } from '../constants';
import PrivateRoute from '../common/PrivateRoute';
import Alert from 'react-s-alert';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
} from "react-router-dom";

class App extends Component {
    constructor(props) {
        super(props);
        this.state = {
            authenticated: false,
            currentUser: null,
            loading: true
        }

        this.loadCurrentlyLoggedInUser = this.loadCurrentlyLoggedInUser.bind(this);
        this.handleLogout = this.handleLogout.bind(this);
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
            currentUser: null
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
                    <Switch>
                        <Route exact path="/">
                            <Home authenticated={this.state.authenticated} onLogout={this.handleLogout}/>
                        </Route>
                        <Route exact path="/post/:id">
                            <Post />
                        </Route>
                        <PrivateRoute exact path="/admin" authenticated={this.state.authenticated}>
                            <Admin />
                        </PrivateRoute>
                        <PrivateRoute path="/profile" authenticated={this.state.authenticated} currentUser={this.state.currentUser}>
                            <Profile />
                        </PrivateRoute>
                        <Route path="/login"
                               render={(props) => <Login authenticated={this.state.authenticated} {...props} />}></Route>
                        <Route path="/signup"
                               render={(props) => <Signup authenticated={this.state.authenticated} {...props} />}></Route>
                        <Route path="/oauth2/redirect">
                            <OAuth2RedirectHandler />
                        </Route>
                        <Route component={NotFound}></Route>
                    </Switch>
            </Router>
        );
    }
}

export default App;
import React from 'react';
import Home from '../home';
import Post from '../post';
import Admin from '../admin';
import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link
  } from "react-router-dom";
const App = () => { 
    return (
        <Router>
            <Switch>
                <Route exact path="/">
                    <Home />
                </Route>
                <Route path="/post/:id">
                    <Post />
                </Route>
                <Route path="/admin">
                    <Admin />
                </Route>
            </Switch>
        </Router> )
}

export default App;
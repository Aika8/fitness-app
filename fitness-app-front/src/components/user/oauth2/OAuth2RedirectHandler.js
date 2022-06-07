import React, { Component } from 'react';
import { ACCESS_TOKEN } from '../../constants';
import { Navigate  } from 'react-router-dom'

class OAuth2RedirectHandler extends Component {
    getUrlParameter(name) {
        var b = document.cookie.match("(^|;)\\s*" + name + "\\s*=\\s*([^;]+)");
        return b ? b.pop() : "";
    };

    render() {        
        const token = this.getUrlParameter("token");

        if(token) {
            localStorage.setItem(ACCESS_TOKEN, token);
            this.props.setAuth();
            return <Navigate  to={{
                pathname: "/",
                state: { from: this.props.location }
            }}/>; 
        } else {
            return <Navigate  to={{
                pathname: "/login",
                state: { 
                    from: this.props.location,
                    error: this.getUrlParameter("error")
                }
            }}/>; 
        }
    }
}

export default OAuth2RedirectHandler;
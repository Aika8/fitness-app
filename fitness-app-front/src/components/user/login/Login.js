import React, {useState, useEffect } from 'react';
import './Login.css';
import { GOOGLE_AUTH_URL, FACEBOOK_AUTH_URL, GITHUB_AUTH_URL, ACCESS_TOKEN } from '../../constants';
import { login } from '../../util/APIUtils';
import { Link, useNavigate  } from 'react-router-dom'
import fbLogo from '../../img/fb-logo.png';
import googleLogo from '../../img/google-logo.png';
import githubLogo from '../../img/github-logo.png';


const Login = ({authenticated, setAuth}) => {
    const navigate = useNavigate();

    useEffect(()=>{
        if(authenticated) {
            return navigate('/');            
        }
    });
        
    return (
        <div className="login-container">
            <div className="login-content">
                <h1 className="login-title">Login to MariaPlume</h1>
                <SocialLogin />
                <div className="or-separator">
                    <span className="or-text">OR</span>
                </div>
                <LoginForm setAuth={setAuth}/>
                <span className="signup-link">New user? <Link to="/signup">Sign up!</Link></span>
            </div>
        </div>
    );
};

const SocialLogin =() => {
 
    return (
        <div className="social-login">
            <a className="btn btn-block social-btn google" href={GOOGLE_AUTH_URL}>
                <img src={googleLogo} alt="Google" /> Log in with Google</a>
        </div>
    );

}

const  AlertDismissibleExample=({show, text})=> {
  
    if (show) {
      return (
        <div class="alert alert-danger" role="alert">
            <p>{text}</p>
        </div>
      );
    } else {
        return (<div></div>);
    }
}

const LoginForm = ({setAuth}) =>  {

    const navigate = useNavigate();
    const [show, setShow] = useState(false);
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [text, setText] = useState('');

    const handleSubmit = (event) => {
        event.preventDefault();   

        const loginRequest = Object.assign({}, {email, password});
        console.log(loginRequest);
        login(loginRequest)
        .then(response => {
            localStorage.setItem(ACCESS_TOKEN, response.accessToken);
            setAuth();
            navigate('/');
        }).catch(error => {
           setText(error.message);
           setShow(true);
        });
    }
    
        return (
            <form onSubmit={handleSubmit}>
                <div className="form-item mb-2">
                    <input type="email" name="email" 
                        className="form-control" placeholder="Email"
                        value={email} onChange={e=>{
                            setEmail(e.target.value);
                            setShow(false); 
                            }} required/>
                </div>
                <div className="form-item">
                    <input type="password" name="password" 
                        className="form-control" placeholder="Password"
                        value={password} onChange={e=>setPassword(e.target.value)} required/>
                </div>
                <div className="form-item">
                    <button type="submit" className="btn btn-block btn-primary">Login</button>
                </div>
                <AlertDismissibleExample show={show} text={text}/>
            </form>                    
        );
}

export default Login;

import React, { Component } from "react"
import googleIcon from "../../img/google.png"
import {GOOGLE_AUTH_URL} from "../../api/Url"

class Login extends Component{

    render(){
        return(
            <div className="login-container">
                <div className="login-content">
                    <NomalLogin />
                    <SocialLogin />
                </div>
            </div>
        );
    }
}
class NomalLogin extends Component{
    render(){
        return(
            <div className="nomal-login">
                <form>
                    <input type="email" name="email" className="login-form" placeholder="Email" required />
                    <input type="password" name="password" className="login-form" placeholder="password" required />
                    <button type="submit" className="login-button">로그인</button>
                </form>
            </div>
        )
    }
}

class SocialLogin extends Component{
    render(){
        return(
            <div className="social-login">
                <a className="google-login" href={GOOGLE_AUTH_URL}>
                    <img src={googleIcon} alt="Google" /> </a>
                </div>
        )
    }
}



export default Login;
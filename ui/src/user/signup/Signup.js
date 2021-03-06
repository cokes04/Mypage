import React, { Component } from "react"
import {GOOGLE_AUTH_URL} from "../../api/Url"
import { signup } from "../../api/Util";
import googleIcon from "../../img/google.png"

class Signup extends Component{

    render(){
        return(
        <div>
            <NomalSignup {...this.props}/>
              <SocialSignup />
        </div>
        );
    }
}

class NomalSignup extends Component{
    constructor(props) {
        super(props);
        this.state = {
            name: '',
            email: '',
            password: ''
        }
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleInputChange(event) {
        const target = event.target;
        const inputName = target.name;        
        const inputValue = target.value;

        this.setState({
            [inputName] : inputValue
        });
    }
    handleSubmit(event) {
        event.preventDefault(); 
        const signupRequest = Object.assign({}, this.state);

        signup(signupRequest)
        .then(response => {
            this.props.history.push({
                pathname : "/login"
            });
        }).catch(error => {
            alert(error);
            this.props.history.push({
                pathname : "/"
            });  
        });
    }

    render(){
        return(
            <div>
               <form  onSubmit={this.handleSubmit}>
               <input type="email" name="email" className="sign-form" placeholder="Email" value={this.state.email} onChange={this.handleInputChange} required />
               <input type="text" name="name" className="sign-form" placeholder="Name" value={this.state.name} onChange={this.handleInputChange} required />
                <input type="password" name="password" className="sign-form" placeholder="password" value={this.state.password} onChange={this.handleInputChange} required />
                <button type="submit" className="sign-button">회원가입</button>
               </form>
            </div>
        )
    }
}

class SocialSignup extends Component{

    render(){
        return(
            <div className="social-sign">
                <a className="google-sign" href={GOOGLE_AUTH_URL}>
                    <img src={googleIcon} alt="Google" /> </a>
                </div>
        )
    }
}
export default Signup;
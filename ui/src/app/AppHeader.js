import React, { Component } from "react";
import { NavLink } from "react-router-dom";
import titleImg from './../img/title.png'

class AppHeader extends Component{

    createLoginBar(){
        let result = [];
        if(this.props.authenticated){ 
            result.push(<NavLink to="/profile" key="0">{this.props.currentUser}aa</NavLink> );
        }
        else{
            result.push(<NavLink to="/login" key="0">로그인</NavLink>);
            result.push(<NavLink to="/signup" key="1">회원가입</NavLink>);
        }
        return result;
    }

    render(){
        return(
        <header className="app-header">
            <NavLink to="/"><img src={titleImg} alt="title" /></NavLink>
            <div className="login-bar">
                {this.createLoginBar()}
            </div>
        </header>
        )
    }
}

export default AppHeader;
import React, { Component } from "react";
import {Switch, Route } from "react-router-dom";
import AppHeader from "./AppHeader";
import Home from "../home/Home";
import Login from "../user/login/Login";
import Signup from "../user/signup/Signup";
import Profile from "../user/profile/Profile";
import OAuth2RedirectHandler from "../user/oauth2/OAuth2RedirectHandler";
import NotFound from "../error/NotFound";
import './App.css';


class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      authenticated: false,
      currentUser : null
    };
  }
  render() {
    return (
      <div className="APP">
        <div>
          <AppHeader authenticated={this.state.authenticated} currentUser={this.state.currentUser}></AppHeader>
        </div>
        <div>
        <Switch>
            <Route exact path="/" component={Home}></Route>           
            <Route path="/login" component={Login}></Route>
            <Route path="/signup" component={Signup}></Route>
            <Route path="/profile" component={Profile}></Route>
            <Route path="/oauth2/redirect" component={OAuth2RedirectHandler}></Route>  
            <Route component={NotFound}></Route>
          </Switch>
        </div>
      </div>
    )
  }
}

export default App;

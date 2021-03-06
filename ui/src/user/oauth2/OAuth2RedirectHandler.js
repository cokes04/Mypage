import React, { Component } from 'react';
import { Redirect } from 'react-router-dom'

class OAuth2RedirectHandler extends Component {
    render() {        

        return (
        <Redirect to={{
            pathname: "/",
            }}/>
            );
     }
   
 }


export default OAuth2RedirectHandler;
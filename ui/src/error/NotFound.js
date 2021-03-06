import React,{ Component } from "react";
import {Link} from "react-router-dom";
class NotFound extends Component{
    render(){
        return(
            <div className="not-found">
            <div className="desc">
                찾을 수 없는 페이지 입니다.
            </div>
            <Link to="/"><button type="button">홈으로</button></Link>
        </div>
        )
    }
}

export default NotFound;
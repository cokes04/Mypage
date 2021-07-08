package com.kog.mypage.novel.exception.server;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
abstract class ServerException extends RuntimeException{
    public HttpStatus httpStatus;

    public ServerException() {
    }

    public ServerException(String message) {
        super(message);
    }
}

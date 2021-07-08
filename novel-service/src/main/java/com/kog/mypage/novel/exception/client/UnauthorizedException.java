package com.kog.mypage.novel.exception.client;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
public class UnauthorizedException extends ClientException {
    public HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public UnauthorizedException() {
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}

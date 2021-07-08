package com.kog.mypage.ticket.exception.client;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UnauthorizedException extends ClientException {
    public HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public UnauthorizedException() {
    }

    public UnauthorizedException(String message) {
        super(message);
    }
}

package com.kog.mypage.ticket.exception.client;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ForbiddenException extends ClientException {
    public HttpStatus httpStatus = HttpStatus.FORBIDDEN;

    public ForbiddenException() {
    }

    public ForbiddenException(String message) {
        super(message);
    }
}

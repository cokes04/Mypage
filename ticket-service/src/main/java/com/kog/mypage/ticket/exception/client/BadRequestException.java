package com.kog.mypage.ticket.exception.client;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends ClientException{
    public HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public BadRequestException() {
    }

    public BadRequestException(String message) {
        super(message);
    }
}

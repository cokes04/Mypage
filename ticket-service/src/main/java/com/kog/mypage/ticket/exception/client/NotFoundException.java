package com.kog.mypage.ticket.exception.client;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NotFoundException extends ClientException {
    public HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }
}

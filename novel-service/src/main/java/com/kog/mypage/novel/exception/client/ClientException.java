package com.kog.mypage.novel.exception.client;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
abstract class ClientException extends RuntimeException {
    public HttpStatus httpStatus;

    public ClientException() {
    }

    public ClientException(String message) {
        super(message);
    }
}

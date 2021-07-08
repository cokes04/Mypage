package com.kog.mypage.novel.controller;

import com.kog.mypage.novel.exception.client.BadRequestException;
import com.kog.mypage.novel.exception.client.NotFoundException;
import com.kog.mypage.novel.exception.client.UnauthorizedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handle(BadRequestException exception){
        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(exception.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handle(UnauthorizedException exception){
        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(exception.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handle(NotFoundException exception){
        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(exception.getMessage());
    }

}

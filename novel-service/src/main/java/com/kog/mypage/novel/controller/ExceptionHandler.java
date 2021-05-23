package com.kog.mypage.novel.controller;

import com.kog.mypage.novel.exception.InaccessibleEntityException;
import com.kog.mypage.novel.exception.NotFoundEntityException;
import com.kog.mypage.novel.exception.OverlapTitleException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(OverlapTitleException.class)
    public ResponseEntity<?> handle(OverlapTitleException exception){
        return ResponseEntity.ok().body(exception.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(InaccessibleEntityException.class)
    public ResponseEntity<?> handle(InaccessibleEntityException exception){
        return ResponseEntity.ok().body(exception.getMessage());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<?> handle(NotFoundEntityException exception){
        return ResponseEntity.ok().body(exception.getMessage());
    }
}

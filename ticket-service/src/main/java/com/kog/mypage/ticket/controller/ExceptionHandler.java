package com.kog.mypage.ticket.controller;

import com.kog.mypage.ticket.exception.LackTicketException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(LackTicketException.class)
    public ResponseEntity<?> handle(LackTicketException exception){
        return ResponseEntity
                .status(exception.getHttpStatus())
                .body(exception.getMessage());
    }

}
package com.kog.mypage.ticket.exception;

import lombok.Getter;

@Getter
public class LackTicketException extends RuntimeException{
    public LackTicketException() {
    }

    public LackTicketException(String message) {
        super(message);
    }
}

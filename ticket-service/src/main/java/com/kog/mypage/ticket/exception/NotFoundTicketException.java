package com.kog.mypage.ticket.exception;

import lombok.Getter;

@Getter
public class NotFoundTicketException extends RuntimeException{
    public NotFoundTicketException() {
    }

    public NotFoundTicketException(String message) {
        super(message);
    }
}

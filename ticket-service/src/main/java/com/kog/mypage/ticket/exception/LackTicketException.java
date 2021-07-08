package com.kog.mypage.ticket.exception;

import com.kog.mypage.ticket.exception.client.ForbiddenException;
import lombok.Getter;

@Getter
public class LackTicketException extends ForbiddenException {
    public LackTicketException() {
    }

    public LackTicketException(String message) {
        super(message);
    }
}

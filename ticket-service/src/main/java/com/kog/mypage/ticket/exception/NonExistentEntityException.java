package com.kog.mypage.ticket.exception;

import com.kog.mypage.ticket.exception.client.NotFoundException;
import lombok.Getter;

@Getter
public class NonExistentEntityException extends NotFoundException {
    public NonExistentEntityException() {
    }

    public NonExistentEntityException(String message) {
        super(message);
    }
}

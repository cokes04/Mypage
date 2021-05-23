package com.kog.mypage.novel.exception;

public class InaccessibleEntityException extends RuntimeException{
    public InaccessibleEntityException() {
    }

    public InaccessibleEntityException(String message) {
        super(message);
    }
}

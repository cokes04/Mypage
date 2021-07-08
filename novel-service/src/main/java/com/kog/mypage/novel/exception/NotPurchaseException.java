package com.kog.mypage.novel.exception;

import lombok.Getter;

@Getter
public class NotPurchaseException extends RuntimeException{

    public NotPurchaseException() {
    }

    public NotPurchaseException(String message) {
        super(message);
    }
}

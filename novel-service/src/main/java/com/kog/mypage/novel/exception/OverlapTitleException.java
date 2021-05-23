package com.kog.mypage.novel.exception;

import lombok.NoArgsConstructor;

public class OverlapTitleException extends RuntimeException{
    public OverlapTitleException() {
    }

    public OverlapTitleException(String message) {
        super(message);
    }
}

package com.kog.mypage.novel.exception;

import lombok.Getter;

@Getter
public class OverlapTitleException extends RuntimeException{

    public OverlapTitleException() {
    }

    public OverlapTitleException(String message) {
        super(message);
    }
}

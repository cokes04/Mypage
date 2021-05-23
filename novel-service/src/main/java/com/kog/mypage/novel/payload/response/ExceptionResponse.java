package com.kog.mypage.novel.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ExceptionResponse extends ApiResponse{
    private int code;

    public ExceptionResponse(boolean success, String message, int code) {
        super(success, message);
        this.code = code;
    }
}

package com.kog.mypage.novel.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ExceptionResponse extends ApiResponse{

    public ExceptionResponse(boolean success, String message) {
        super(success, message);
    }
}

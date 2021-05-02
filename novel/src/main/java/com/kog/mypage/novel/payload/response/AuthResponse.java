package com.kog.mypage.novel.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class AuthResponse extends ApiResponse{
    private String accessToken;
    private String tokenType = "Bearer";

    public AuthResponse(boolean success, String message, String accessToken) {
        super(success, message);
        this.accessToken = accessToken;
    }
}

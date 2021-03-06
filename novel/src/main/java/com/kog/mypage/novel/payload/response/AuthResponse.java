package com.kog.mypage.novel.payload.response;

import lombok.Getter;

@Getter
public class AuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}

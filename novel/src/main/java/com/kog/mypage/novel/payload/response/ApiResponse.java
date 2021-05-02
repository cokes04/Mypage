package com.kog.mypage.novel.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiResponse {

    private boolean success;

    private String message;
}
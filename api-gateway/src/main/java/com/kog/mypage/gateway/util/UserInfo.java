package com.kog.mypage.gateway.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@AllArgsConstructor
@Getter
@ToString
public class UserInfo {
    private Long userId;
    private String email;
    private List<String> roles;
    private boolean isExpired;

}
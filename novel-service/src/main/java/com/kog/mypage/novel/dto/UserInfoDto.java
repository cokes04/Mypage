package com.kog.mypage.novel.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class UserInfoDto {

    private Long userId;

    private List<String> roles;

    @Builder
    public UserInfoDto(Long userId, List<String> roles) {
        this.userId = userId;
        this.roles = roles;
    }
}

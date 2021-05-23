package com.kog.mypage.ticket.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class UserInfoDto {

    Long userId;

    List<String> roles;

    @Builder
    public UserInfoDto(Long userId, List<String> roles) {
        this.userId = userId;
        this.roles = roles;
    }
}

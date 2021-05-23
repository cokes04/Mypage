package com.kog.mypage.auth.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kog.mypage.auth.entity.User;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class UserProfileResponse extends ApiResponse{
    private Long userId;

    private String name;

    private String email;

    private String authProvider;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    @Builder
    public UserProfileResponse(boolean success, String message, Long userId, String name, String email, String authProvider, LocalDateTime createdDate) {
        super(success, message);
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.authProvider = authProvider;
        this.createdDate = createdDate;
    }


    public static UserProfileResponse of(boolean success, String message, User user){
        return UserProfileResponse.builder()
                .success(success)
                .message(message)
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .authProvider(user.getAuthProvider() == null ? "" : user.getAuthProvider().toString())
                .createdDate(user.getCreatedDate())
                .build();
    }
}

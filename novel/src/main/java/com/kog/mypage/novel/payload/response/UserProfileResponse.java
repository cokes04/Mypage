package com.kog.mypage.novel.payload.response;

import com.kog.mypage.novel.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserProfile extends ApiResponse{
    private Long userId;

    private String name;

    private String email;

    private String authProvider;

    @Builder
    public UserProfile(boolean success, String message, Long userId, String name, String email, String authProvider) {
        super(success, message);
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.authProvider = authProvider;
    }

    public static UserProfile of(boolean success, String message, User user){
        return UserProfile.builder()
                .success(success)
                .message(message)
                .name(user.getName())
                .email(user.getEmail())
                .authProvider(user.getAuthProvider().toString())
                .userId(user.getId())

                .build();
    }
}

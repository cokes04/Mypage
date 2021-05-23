package com.kog.mypage.cash.payload.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AuthorizationInfo {

    @Positive
    @NotBlank
    Long id;

    @Email
    @NotBlank
    String email;

    @NotBlank
    List<String> roles;

    @NotBlank
    LocalDateTime exp;
}

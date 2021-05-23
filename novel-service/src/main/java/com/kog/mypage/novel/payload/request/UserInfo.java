package com.kog.mypage.novel.payload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
public class UserInfo {

    @Max(Long.MAX_VALUE)
    @Min(1)
    private Long userId;

    @Email
    @NotBlank
    private String email;

    private List<String> roles;

    private boolean expired;
}

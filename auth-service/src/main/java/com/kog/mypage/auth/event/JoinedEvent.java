package com.kog.mypage.auth.event;

import com.kog.mypage.auth.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JoinedEvent {
    private Long userId;

}

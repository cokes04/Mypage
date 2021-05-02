package com.kog.mypage.novel.entity.enumerate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Genre {
    FANTASY("판타지"), ROMANCE("로맨스"), MARTIAL_ARTS("무협");

    public final String value;
}

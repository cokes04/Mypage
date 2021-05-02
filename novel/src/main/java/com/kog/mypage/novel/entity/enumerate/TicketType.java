package com.kog.mypage.novel.entity.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TicketType {
    RENTAL("TR"), POSSESSION("TP");

    public final String value;
}

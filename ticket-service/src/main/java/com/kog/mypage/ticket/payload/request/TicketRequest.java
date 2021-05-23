package com.kog.mypage.ticket.payload.request;

import lombok.Getter;

@Getter
public class TicketRequest {
    private Long novelId;
    private UserInfo userInfo;
}

package com.kog.mypage.ticket.payload.request;

import lombok.Getter;

@Getter
public class AddTicketRequest {
    private UserInfo userInfo;

    private Long novelId;

    private String type;

    private int count;

    private int paidCount;

    private int price;
}

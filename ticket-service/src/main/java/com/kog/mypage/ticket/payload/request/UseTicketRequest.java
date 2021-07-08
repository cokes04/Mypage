package com.kog.mypage.ticket.payload.request;

import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.Positive;

@Getter
@ToString
public class UseTicketRequest {
    private UserInfo userInfo;

    @Positive
    private Long novelId;

    @Positive
    private Long episodeId;

    private String type;
}
package com.kog.mypage.ticket.payload.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TicketPageRequest {
    private int page;

    private int size;
}

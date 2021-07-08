package com.kog.mypage.ticket.payload.request;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.Optional;

@Getter
public class TicketRecordPageRequest {

    private int page;

    private int size;

    private String standard;
}

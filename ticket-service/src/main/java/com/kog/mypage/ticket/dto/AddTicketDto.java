package com.kog.mypage.ticket.dto;

import com.kog.mypage.ticket.enumeration.TicketType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddTicketDto extends ChangeTicketDto {

    private int count;

    private int paidCount;

    private int price;

    @Builder
    public AddTicketDto(Long userId, Long novelId, TicketType ticketType, int count, int paidCount, int price) {
        super(userId, novelId, ticketType);
        this.count = count;
        this.paidCount = paidCount;
        this.price = price;
    }

}

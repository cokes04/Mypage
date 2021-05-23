package com.kog.mypage.ticket.dto;

import com.kog.mypage.ticket.entity.enumerate.TicketType;
import com.kog.mypage.ticket.entity.AddTicketRecord;
import com.kog.mypage.ticket.entity.Ticket;
import com.kog.mypage.ticket.entity.TicketRecord;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddTicketDto extends ChangeTicketDto {

    private int count;

    private int paidCount;

    private int price;

    @Builder
    public AddTicketDto(Long novelId, TicketType ticketType, int count, int paidCount, int price) {
        super(novelId, ticketType);
        this.count = count;
        this.paidCount = paidCount;
        this.price = price;
    }

}

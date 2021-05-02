package com.kog.mypage.novel.dto;

import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.enumerate.TicketType;
import com.kog.mypage.novel.entity.ticket.AddTicketRecord;
import com.kog.mypage.novel.entity.ticket.Ticket;
import com.kog.mypage.novel.entity.ticket.TicketRecord;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AddTicketDto extends ChangeTicketDto {

    private int count;

    private int paidCount;

    private int price;

    @Builder
    public AddTicketDto(User user, Novel novel, TicketType ticketType, int count, int paidCount, int price) {
        super(user, novel, ticketType);
        this.count = count;
        this.paidCount = paidCount;
        this.price = price;
    }

    @Override
    public TicketRecord createTicketRecord() {
        return AddTicketRecord.builder()
                .user(getUser())
                .novel(getNovel())
                .count(this.count)
                .paidCount(this.paidCount)
                .price(this.price)
                .ticketType(getTicketType())
                .build();
    }
    public Ticket createTicket(){
        Ticket ticket = Ticket.builder()
                .user(getUser())
                .novel(getNovel())
                .rentalCount(0)
                .possessionCount(0)
                .build();
        ticket.changeCount(count, getTicketType());

        return ticket;
    }
}

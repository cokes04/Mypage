package com.kog.mypage.novel.dto;

import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.enumerate.TicketType;
import com.kog.mypage.novel.entity.ticket.Ticket;
import com.kog.mypage.novel.entity.ticket.TicketRecord;
import com.kog.mypage.novel.entity.ticket.UseTicketRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public abstract class TicketDto {

    private User user;

    private Novel novel;

    private TicketType ticketType;

    private int count;

    public Ticket createTicket(){
         Ticket ticket = Ticket.builder()
                 .user(this.user)
                 .novel(this.novel)
                 .rentalCount(0)
                 .possessionCount(0)
                 .build();
         ticket.changeCount(this.count, this.ticketType);
         return ticket;
    }

    public abstract TicketRecord createTicketRecord();

}

package com.kog.mypage.novel.service;

import com.kog.mypage.novel.dto.AddTicketDto;
import com.kog.mypage.novel.dto.TicketDto;
import com.kog.mypage.novel.dto.UseTicketDto;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.ticket.Ticket;

import java.util.Optional;

public interface TicketService {

    public Optional<Ticket> getTicketByUserAndNovel(User user, Novel novel);
    public Ticket useTicket(UseTicketDto ticketDto);
    public Ticket addTicket(AddTicketDto ticketDto);
}

package com.kog.mypage.ticket.service;

import com.kog.mypage.ticket.dto.AddTicketDto;
import com.kog.mypage.ticket.dto.UseTicketDto;
import com.kog.mypage.ticket.entity.Ticket;
import com.kog.mypage.ticket.exception.LackTicketException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TicketService {


    Ticket getTicketByNovelIdAndUserId(Long novelId, Long userId);

    Page<Ticket> getTicketByUserId(Long userId, Pageable pageable);

    Ticket useTicket(UseTicketDto ticketDto)
            throws LackTicketException;

    Ticket addTicket(AddTicketDto ticketDto);
}

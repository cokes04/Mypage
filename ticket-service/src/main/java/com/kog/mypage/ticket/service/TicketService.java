package com.kog.mypage.ticket.service;

import com.kog.mypage.ticket.dto.AddTicketDto;
import com.kog.mypage.ticket.dto.UseTicketDto;
import com.kog.mypage.ticket.dto.UserInfoDto;
import com.kog.mypage.ticket.entity.AddTicketRecord;
import com.kog.mypage.ticket.entity.Ticket;
import com.kog.mypage.ticket.entity.UseTicketRecord;
import com.kog.mypage.ticket.exception.LackTicketException;
import com.kog.mypage.ticket.exception.NotFoundTicketException;
import com.kog.mypage.ticket.payload.request.UserInfo;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface TicketService {


    Ticket getTicketByNovelId(Long novelId, UserInfoDto userInfoDto);

    Ticket useTicket(UseTicketDto ticketDto, UserInfoDto userInfoDto)
            throws LackTicketException;

    Ticket addTicket(AddTicketDto ticketDto, UserInfoDto userInfoDto);
}

package com.kog.mypage.novel.service;

import com.kog.mypage.novel.dto.AddTicketDto;
import com.kog.mypage.novel.dto.UseTicketDto;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.ticket.AddTicketRecord;
import com.kog.mypage.novel.entity.ticket.Ticket;
import com.kog.mypage.novel.entity.ticket.TicketRecord;
import com.kog.mypage.novel.entity.ticket.UseTicketRecord;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface TicketService {


    public Optional<Ticket> getTicketByUserIdAndNovelId(Long userId, Long novelId);

    public Ticket useTicket(UseTicketDto ticketDto);

    public Ticket addTicket(AddTicketDto ticketDto);

    public Ticket cancelTicket(AddTicketDto ticketDto);

    public Ticket createNewTicket(User user, Novel novel);

    public Page<AddTicketRecord> getAddTicketRecordByUserIdAndNovelId(Long userId, Optional<Long> optionalNovelId, int pageNum);

    public Page<UseTicketRecord> getUseTicketRecordByUserIdAndNovelId(Long userId, Optional<Long> optionalNovelId, int pageNum);
}

package com.kog.mypage.ticket.service;

import com.kog.mypage.ticket.dto.AddTicketDto;
import com.kog.mypage.ticket.dto.UseTicketDto;
import com.kog.mypage.ticket.dto.UserInfoDto;
import com.kog.mypage.ticket.entity.Ticket;
import com.kog.mypage.ticket.exception.NotFoundTicketException;
import com.kog.mypage.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class TicketServiceImpl implements TicketService{

    private final TicketRepository ticketRepository;

    private final TicketRecordService ticketRecordService;


    @Transactional
    @Override
    public Ticket getTicketByNovelId(Long novelId, UserInfoDto userInfoDto){
        Long userId = userInfoDto.getUserId();

        Ticket ticket = ticketRepository.findByNovelIdAndUserId(novelId, userId)
                .orElse(createNewZeroTicket(novelId, userId));

        return ticket;
    }

    @Override
    public Ticket useTicket(UseTicketDto ticketDto, UserInfoDto userInfoDto) {
        Long userId = userInfoDto.getUserId();

        Ticket ticket = ticketRepository.findByNovelIdAndUserId(ticketDto.getNovelId(), userId)
                .orElse( createNewZeroTicket(ticketDto.getNovelId(), userId));

        if(!ticketRecordService.isPurchasedEpisode(ticketDto.getEpisodeId(), userInfoDto)) { // 보유 or 대여중이 아니라면
            ticket.changeCount(-1, ticketDto.getTicketType());
            ticketRecordService.createUseTicketRecord(ticketDto, userInfoDto);
            ticket = ticketRepository.save(ticket);
        }

        return  ticket;
    }

    @Override
    public Ticket addTicket(AddTicketDto ticketDto, UserInfoDto userInfoDto) {
        Long userId = userInfoDto.getUserId();

        Ticket ticket = ticketRepository.findByNovelIdAndUserId(ticketDto.getNovelId(), userId)
                .orElse(createNewZeroTicket(ticketDto.getNovelId(),userId));

        ticket.changeCount(ticketDto.getCount(), ticketDto.getTicketType());


        ticketRecordService.createAddTicketRecord(ticketDto, userInfoDto);
        return ticketRepository.save(ticket);
    }

    @Transactional
    private Ticket createNewZeroTicket(Long novelId, Long userId) {
        Ticket newTicket = Ticket.builder()
                .userId(userId)
                .novelId(novelId)
                .possessionCount(0)
                .rentalCount(0)
                .build();
        return newTicket;
    }

}


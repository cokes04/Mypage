package com.kog.mypage.ticket.service;

import com.kog.mypage.ticket.dto.AddTicketDto;
import com.kog.mypage.ticket.dto.UseTicketDto;
import com.kog.mypage.ticket.entity.Ticket;
import com.kog.mypage.ticket.event.event.AddedTicketEvent;
import com.kog.mypage.ticket.event.event.UsedTicketEvent;
import com.kog.mypage.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class TicketServiceImpl implements TicketService{

    private final TicketRepository ticketRepository;
    private final TicketRecordService ticketRecordService;
    private final ApplicationEventPublisher publisher;

    @Transactional
    @Override
    public Ticket getTicketByNovelIdAndUserId(Long novelId, Long userId){
        Ticket ticket = ticketRepository.findByNovelIdAndUserId(novelId, userId)
                .orElse(createNewZeroTicket(novelId, userId));

        return ticket;
    }

    @Transactional
    @Override
    public Page<Ticket> getTicketByUserId(Long userId, Pageable pageable) {
        Page<Ticket> tickets = ticketRepository.findByUserId(userId, pageable);
        return tickets;
    }

    @Transactional
    @Override
    public Ticket useTicket(UseTicketDto ticketDto) {
        Long userId = ticketDto.getUserId();

        Ticket ticket = ticketRepository.findByNovelIdAndUserId(ticketDto.getNovelId(), userId)
                .orElse( createNewZeroTicket(ticketDto.getNovelId(), userId));

        Ticket resultTicket;
        if(!ticketRecordService.isPurchasedEpisode(ticketDto.getEpisodeId(), userId)) { // 보유 or 대여중이 아니라면
            ticket.changeCount(-1, ticketDto.getTicketType());
            ticketRecordService.createUseTicketRecord(ticketDto);
            resultTicket = ticketRepository.save(ticket);
            publisher.publishEvent(new UsedTicketEvent());
            return  resultTicket;
        }else
            return ticket;
        // else 면?


    }

    @Transactional
    @Override
    public Ticket addTicket(AddTicketDto ticketDto) {
        Long userId = ticketDto.getUserId();

        Ticket ticket = ticketRepository.findByNovelIdAndUserId(ticketDto.getNovelId(), userId)
                .orElse(createNewZeroTicket(ticketDto.getNovelId(),userId));

        ticket.changeCount(ticketDto.getCount(), ticketDto.getTicketType());

        ticketRecordService.createAddTicketRecord(ticketDto);
        Ticket resultTicket = ticketRepository.save(ticket);
        publisher.publishEvent(new AddedTicketEvent());
        return resultTicket;
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


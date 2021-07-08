package com.kog.mypage.ticket.runner;

import com.kog.mypage.ticket.dto.AddTicketDto;
import com.kog.mypage.ticket.enumeration.TicketType;
import com.kog.mypage.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner{

    private final TicketService ticketService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        AddTicketDto addTicketDto = AddTicketDto.builder()
                .userId(1l)
                .novelId(100l)
                .ticketType(TicketType.POSSESSION)
                .count(11)
                .paidCount(10)
                .price(1000)
                .build();

        ticketService.addTicket(addTicketDto);
    }
}
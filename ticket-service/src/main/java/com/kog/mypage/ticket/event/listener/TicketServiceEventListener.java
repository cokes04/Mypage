package com.kog.mypage.ticket.event.listener;

import com.kog.mypage.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TicketServiceEventListener {

    private final TicketService ticketService;

    @EventListener
    public void handleEvent(Long event){
    }

}

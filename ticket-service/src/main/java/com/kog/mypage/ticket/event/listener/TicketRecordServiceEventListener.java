package com.kog.mypage.ticket.event.listener;

import com.kog.mypage.ticket.service.TicketRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class TicketRecordServiceEventListener {
    private final TicketRecordService ticketRecordService;
}

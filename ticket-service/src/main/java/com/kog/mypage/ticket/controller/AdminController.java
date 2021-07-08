package com.kog.mypage.ticket.controller;

import com.kog.mypage.ticket.service.TicketRecordService;
import com.kog.mypage.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/ticket")
public class AdminController {
    private final TicketService ticketService;
    private final TicketRecordService ticketRecordService;

}

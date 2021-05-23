package com.kog.mypage.novel.event;

import com.kog.mypage.novel.service.CashService;
import com.kog.mypage.novel.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CashServiceEventHandler {

    private final CashService cashService;

    @EventListener // 1
    public void e(JoinedEvent event) {
        cashService.createZeroCash(event.getUser());
    }
}
package com.kog.mypage.ticket.dto;

import com.kog.mypage.ticket.entity.enumerate.TicketType;
import com.kog.mypage.ticket.entity.TicketRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public abstract class ChangeTicketDto {

    private Long novelId;

    private TicketType ticketType;

}

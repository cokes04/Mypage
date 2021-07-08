package com.kog.mypage.ticket.dto;

import com.kog.mypage.ticket.enumeration.TicketType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public abstract class ChangeTicketDto {

    private Long userId;

    private Long novelId;

    private TicketType ticketType;

}

package com.kog.mypage.ticket.dto;

import com.kog.mypage.ticket.enumeration.TicketType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UseTicketDto extends ChangeTicketDto {

    private Long episodeId;

    @Builder
    public UseTicketDto(Long userId, Long novelId, TicketType ticketType, Long episodeId) {
        super(userId, novelId, ticketType);
        this.episodeId =  episodeId;
    }
}

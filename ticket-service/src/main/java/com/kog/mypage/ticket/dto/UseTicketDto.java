package com.kog.mypage.ticket.dto;

import com.kog.mypage.ticket.entity.enumerate.TicketType;
import com.kog.mypage.ticket.entity.TicketRecord;
import com.kog.mypage.ticket.entity.UseTicketRecord;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UseTicketDto extends ChangeTicketDto {

    private Long episodeId;

    @Builder
    public UseTicketDto(Long novelId, TicketType ticketType, Long episodeId) {
        super(novelId, ticketType);
        this.episodeId =  episodeId;
    }
}

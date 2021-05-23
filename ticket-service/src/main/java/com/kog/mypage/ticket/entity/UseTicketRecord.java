package com.kog.mypage.ticket.entity;

import com.kog.mypage.ticket.entity.converter.BooleanToYNConverter;
import com.kog.mypage.ticket.entity.enumerate.TicketType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@ToString
@Getter
@Entity
@DiscriminatorValue("USE")
public class UseTicketRecord extends TicketRecord{

    @Convert(converter = BooleanToYNConverter.class)
    private boolean paidTicket;

    private Long episodeId;

    @Builder
    public UseTicketRecord(Long id, Long userId, Long novelId, TicketType ticketType, LocalDateTime createdDate, boolean paidTicket, Long episodeId) {
        super(id, userId, novelId, ticketType, createdDate);
        this.paidTicket = paidTicket;
        this.episodeId = episodeId;
    }
}

package com.kog.mypage.novel.entity.ticket;

import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.converter.BooleanToYNConverter;
import com.kog.mypage.novel.entity.enumerate.TicketType;
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

    @ManyToOne
    @JoinColumn(name = "EPISODE_ID")
    private Episode episode;

    @Builder
    public UseTicketRecord(Long id, User user, Novel novel, TicketType ticketType, LocalDateTime createdDate, boolean paidTicket, Episode episode) {
        super(id, user, novel, ticketType, createdDate);
        this.paidTicket = paidTicket;
        this.episode = episode;
    }
}

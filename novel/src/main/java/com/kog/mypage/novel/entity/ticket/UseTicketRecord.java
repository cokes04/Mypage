package com.kog.mypage.novel.entity.ticket;

import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.enumerate.TicketType;
import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@ToString
@Getter
@Entity
@DiscriminatorValue("USE")
public class UseTicketRecord extends TicketRecord{

    @ManyToOne
    @JoinColumn(name = "EPISODE_ID")
    private Episode episode;

    @Builder
    public UseTicketRecord(Long id, User user, Novel novel, TicketType ticketType, int count, LocalDateTime createdDate, Episode episode) {
        super(id, user, novel, ticketType, count, createdDate);
        this.episode = episode;
    }
}

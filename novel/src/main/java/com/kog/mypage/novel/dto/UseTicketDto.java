package com.kog.mypage.novel.dto;

import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.enumerate.TicketType;
import com.kog.mypage.novel.entity.ticket.TicketRecord;
import com.kog.mypage.novel.entity.ticket.UseTicketRecord;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
public class UseTicketDto extends TicketDto {

    private Episode episode;

    @Builder
    public UseTicketDto(User user, Novel novel, TicketType ticketType, Episode episode) {
        super(user, novel, ticketType, -1);
        this.episode =  episode;
    }

    @Override
    public TicketRecord createTicketRecord() {
        return UseTicketRecord.builder()
                .user(this.getUser())
                .episode(this.episode)
                .count(this.getCount())
                .ticketType(this.getTicketType())
                .build();
    }
}

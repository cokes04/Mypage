package com.kog.mypage.novel.dto;

import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.enumerate.TicketType;
import com.kog.mypage.novel.entity.ticket.AddTicketRecord;
import com.kog.mypage.novel.entity.ticket.TicketRecord;
import com.kog.mypage.novel.entity.ticket.UseTicketRecord;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
public class AddTicketDto extends TicketDto{
    @Builder
    public AddTicketDto(User user, Novel novel, TicketType ticketType, int count) {
        super(user, novel, ticketType, count);
    }

    @Override
    public TicketRecord createTicketRecord() {
        return AddTicketRecord.builder()
                .user(this.getUser())
                .novel(this.getNovel())
                .count(this.getCount())
                .ticketType(this.getTicketType())
                .build();
    }
}

package com.kog.mypage.novel.entity.ticket;

import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.enumerate.TicketType;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Entity
@Getter
@DiscriminatorValue("ADD")
public class AddTicketRecord extends TicketRecord {

    @Builder
    public AddTicketRecord(Long id, User user, Novel novel, TicketType ticketType, int count, LocalDateTime createdDate) {
        super(id, user, novel, ticketType, count, createdDate);
    }

}

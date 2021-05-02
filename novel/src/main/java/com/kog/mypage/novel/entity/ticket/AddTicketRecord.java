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

    private int count;

    private int paidCount;

    private int price;

    @Builder
    public AddTicketRecord(Long id, User user, Novel novel, TicketType ticketType, LocalDateTime createdDate, int count, int paidCount, int price) {
        super(id, user, novel, ticketType, createdDate);
        this.count = count;
        this.paidCount = paidCount;
        this.price = price;
    }
}

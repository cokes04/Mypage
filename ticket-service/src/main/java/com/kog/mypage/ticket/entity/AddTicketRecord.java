package com.kog.mypage.ticket.entity;

import com.kog.mypage.ticket.enumeration.TicketType;

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
    public AddTicketRecord(Long id, Long userId, Long novelId, TicketType ticketType, LocalDateTime createdDate, int count, int paidCount, int price) {
        super(id, userId, novelId, ticketType, createdDate);
        this.count = count;
        this.paidCount = paidCount;
        this.price = price;
    }
}

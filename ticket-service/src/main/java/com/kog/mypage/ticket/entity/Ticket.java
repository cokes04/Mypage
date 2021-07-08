package com.kog.mypage.ticket.entity;

import com.kog.mypage.ticket.enumeration.TicketType;
import com.kog.mypage.ticket.exception.LackTicketException;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@Getter
@Entity
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Ticket {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private Long novelId;

    private int rentalCount;

    private int possessionCount;

    @CreatedDate
    private LocalDateTime createdDate;

    @Builder
    public Ticket(Long id, Long userId, Long novelId, int rentalCount, int possessionCount, LocalDateTime createdDate) {
        this.id = id;
        this.userId = userId;
        this.novelId = novelId;
        this.rentalCount = rentalCount;
        this.possessionCount = possessionCount;
        this.createdDate = createdDate;
    }

    public int getAllCount(){
        return rentalCount + possessionCount;
    }

    public void changeCount(int count, TicketType ticketType){
        if(ticketType == TicketType.POSSESSION){
            changePossessionCount(count);
        }
        else if(ticketType == TicketType.RENTAL){
            changeRentalCount(count);
        }
        else {
            throw new RuntimeException("티켓 타입 불명");
        }
    }

    private void changePossessionCount(int count){
        if(this.possessionCount + count >= 0){
            this.possessionCount += count;
        }
        else{
            throw new LackTicketException();
        }
    }

    private void changeRentalCount(int count){
        if(this.rentalCount + count >= 0){
            this.rentalCount += count;
        }
        else{
            throw new LackTicketException();
        }
    }
}

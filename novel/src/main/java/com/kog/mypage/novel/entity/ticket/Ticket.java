package com.kog.mypage.novel.entity.ticket;


import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.enumerate.TicketType;
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

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "NOVEL_ID")
    private Novel novel;

    private int rentalCount;

    private int possessionCount;

    @CreatedDate
    private LocalDateTime createDate;

    @Builder
    public Ticket(Long id, User user, Novel novel, int rentalCount, int possessionCount, LocalDateTime createDate) {
        this.id = id;
        this.user = user;
        this.novel = novel;
        this.rentalCount = rentalCount;
        this.possessionCount = possessionCount;
        this.createDate = createDate;
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
            throw new RuntimeException("티켓 갯수 부족");
        }
    }

    private void changeRentalCount(int count){
        if(this.rentalCount + count >= 0){
            this.rentalCount += count;
        }
        else{
            throw new RuntimeException("티켓 갯수 부족");
        }
    }
}

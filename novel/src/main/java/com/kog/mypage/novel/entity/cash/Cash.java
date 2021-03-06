package com.kog.mypage.novel.entity.cash;

import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.enumerate.CashType;
import com.kog.mypage.novel.entity.enumerate.TicketType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Cash {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "USER_ID", unique = true)
    private User user;

    @Column(nullable = false)
    private int buy_amount;

    @Column(nullable = false)
    private int event_amount;

    @Builder
    public Cash(Long id, User user, int event_amount, int buy_amount) {
        this.id = id;
        this.user = user;
        this.event_amount = event_amount;
        this.buy_amount = buy_amount;
    }

    public int getAllAmount() {
        return event_amount + buy_amount;
    }

    public void changeAmount(int amount, CashType cashType) {
        if (cashType == CashType.EVENT_CASH) {
            changeBuyCount(amount);
        } else if (cashType == CashType.BUY_CASH) {
            changeEventCount(amount);
        } else {
            throw new RuntimeException("캐시 타입 불명");
        }
    }

    private void changeBuyCount(int amount) {
        if (this.buy_amount + amount >= 0) {
            this.buy_amount += amount;
        } else {
            throw new RuntimeException("캐시 부족");
        }
    }

    private void changeEventCount(int amount) {
        if (this.event_amount + amount >= 0) {
            this.event_amount += amount;
        } else {
            throw new RuntimeException("캐시 부족");
        }
    }
}
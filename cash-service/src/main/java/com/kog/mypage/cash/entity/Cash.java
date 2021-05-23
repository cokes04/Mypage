package com.kog.mypage.cash.entity;

import com.kog.mypage.cash.entity.enumerate.CashType;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Cash {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    @Column(nullable = false)
    private int paidAmount;

    @Column(nullable = false)
    private int freeAmount;

    @Builder
    public Cash(Long id, Long userId, int freeAmount, int paidAmount) {
        this.id = id;
        this.userId = userId;
        this.freeAmount = freeAmount;
        this.paidAmount = paidAmount;
    }

    public int getTotalAmount() {
        return freeAmount + paidAmount;
    }

    public boolean changeCash(int amount, CashType cashType) {
        if (cashType == CashType.PAID_CASH)
            return changePaidAmount(amount);
        else if (cashType == CashType.FREE_CASH)
            return changeFreeAmount(amount);
        else
            throw new RuntimeException("캐시 타입 불명");
    }

    private boolean changePaidAmount(int amount) {
        if (this.paidAmount + amount >= 0) {
            this.paidAmount += amount;
            return true;
        } else
            return false;
    }

    private boolean changeFreeAmount(int amount) {
        if (this.freeAmount + amount >= 0) {
            this.freeAmount += amount;
            return true;
        } else
            return false;

    }
}
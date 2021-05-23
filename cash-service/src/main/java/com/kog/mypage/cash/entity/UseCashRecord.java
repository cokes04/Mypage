package com.kog.mypage.cash.entity;


import lombok.Builder;
import lombok.Getter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;


@Entity
@Getter
@DiscriminatorValue("USE")
public class UseCashRecord extends CashRecord{

    @ManyToOne
    private Long novelId;

    @Builder
    public UseCashRecord(Long id, Long userId, int freeAmount, int paidAmount, LocalDateTime createdDate, Long novelId) {
        super(id, userId, freeAmount, paidAmount, createdDate);
        this.novelId = novelId;
    }
}

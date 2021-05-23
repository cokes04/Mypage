package com.kog.mypage.cash.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Setter(value = AccessLevel.PRIVATE)
@Getter
@DiscriminatorValue("ADD")
public class AddCashRecord extends CashRecord{

    @Builder
    public AddCashRecord(Long id, Long userId, int freeAmount, int paidAmount, LocalDateTime createdDate) {
        super(id, userId, freeAmount, paidAmount, createdDate);
    }
}

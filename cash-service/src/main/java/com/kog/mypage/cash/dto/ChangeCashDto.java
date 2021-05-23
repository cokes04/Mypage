package com.kog.mypage.cash.dto;

import com.kog.mypage.cash.entity.CashRecord;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class ChangeCashDto {
    private Long userId;

    private int freeAmount;

    private int paidAmount;

    public abstract CashRecord createCashRecord();
}

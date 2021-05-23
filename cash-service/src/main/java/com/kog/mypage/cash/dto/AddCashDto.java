package com.kog.mypage.cash.dto;

import com.kog.mypage.cash.entity.AddCashRecord;
import com.kog.mypage.cash.entity.CashRecord;
import lombok.Builder;
import lombok.Getter;

@Getter
public final class AddCashDto extends ChangeCashDto {

    @Builder
    public AddCashDto(Long userId, int freeAmount, int paidAmount) {
        super(userId, freeAmount, paidAmount);
    }

    @Override
    public CashRecord createCashRecord() {
        return AddCashRecord.builder()
                .userId(this.getUserId())
                .paidAmount(this.getPaidAmount())
                .freeAmount(this.getFreeAmount())
                .build();
    }
}

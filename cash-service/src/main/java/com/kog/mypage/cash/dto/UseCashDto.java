package com.kog.mypage.cash.dto;

import com.kog.mypage.cash.entity.CashRecord;
import com.kog.mypage.cash.entity.UseCashRecord;
import lombok.Builder;
import lombok.Getter;

@Getter
public final class UseCashDto extends ChangeCashDto{
    private final Long novelId;

    @Builder
    public UseCashDto(Long userId, int freeAmount, int paidAmount, Long novelId) {
        super(userId, freeAmount, paidAmount);
        this.novelId = novelId;
    }

    @Override
    public CashRecord createCashRecord() {
        return UseCashRecord.builder()
                .userId(this.getUserId())
                .paidAmount(this.getPaidAmount())
                .freeAmount(this.getFreeAmount())
                .novelId(this.getNovelId()).build();
    }
}

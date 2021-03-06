package com.kog.mypage.novel.dto;

import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.cash.AddCashRecord;
import com.kog.mypage.novel.entity.cash.CashRecord;
import com.kog.mypage.novel.entity.cash.UseCashRecord;
import com.kog.mypage.novel.entity.enumerate.CashType;
import lombok.Builder;
import lombok.Getter;

@Getter
public final class UseCashDto extends CashDto{
    private final Novel novel;

    @Builder
    public UseCashDto(User user, CashType cashType, int amount, Novel novel) {
        super(user, cashType, amount);
        if (amount >= 0){
            throw new RuntimeException("0이하여야함");
        }
        this.novel = novel;
    }

    @Override
    public CashRecord createCashRecord() {
        return UseCashRecord.builder()
                .user(this.getUser())
                .cashType(this.getCashType())
                .amount(this.getAmount())
                .novel(this.novel).build();
    }
}

package com.kog.mypage.novel.dto;

import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.cash.AddCashRecord;
import com.kog.mypage.novel.entity.cash.CashRecord;
import com.kog.mypage.novel.entity.enumerate.CashType;
import com.kog.mypage.novel.entity.ticket.Ticket;
import lombok.Builder;
import lombok.Getter;

@Getter
public final class AddCashDto extends CashDto {
    @Builder
    public AddCashDto(User user, CashType cashType, int amount) {
        super(user, cashType, amount);
    }


    @Override
    public CashRecord createCashRecord() {
        return AddCashRecord.builder()
                .user(this.getUser())
                .cashType(this.getCashType())
                .amount(this.getAmount()).build();
    }
}

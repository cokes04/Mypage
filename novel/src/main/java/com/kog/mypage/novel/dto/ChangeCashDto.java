package com.kog.mypage.novel.dto;

import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.cash.Cash;
import com.kog.mypage.novel.entity.cash.CashRecord;
import com.kog.mypage.novel.entity.enumerate.CashType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class ChangeCashDto {
    private User user;

    private int amount;

    public abstract CashRecord createCashRecord();
}

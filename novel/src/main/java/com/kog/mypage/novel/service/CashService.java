package com.kog.mypage.novel.service;

import com.kog.mypage.novel.dto.AddCashDto;
import com.kog.mypage.novel.dto.UseCashDto;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.cash.Cash;

import java.util.Optional;

public interface CashService {

    public Cash getCashByUser(User user);
    public Cash useCash(UseCashDto cashDto);
    public Cash addCash(AddCashDto cashDto);
}

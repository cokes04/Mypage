package com.kog.mypage.cash.service;

import com.kog.mypage.cash.dto.AddCashDto;
import com.kog.mypage.cash.dto.UseCashDto;
import com.kog.mypage.cash.entity.AddCashRecord;
import com.kog.mypage.cash.entity.Cash;
import com.kog.mypage.cash.entity.UseCashRecord;
import org.springframework.data.domain.Page;

public interface CashService {

    Cash getCashByUserId(Long userId);

    Cash useCash(UseCashDto cashDto);

    Cash addCash(AddCashDto cashDto);

    Page<AddCashRecord> getAddCashRecordByUserId(Long userId, int pageNum);

    Page<UseCashRecord> getUseCashRecordByUserId(Long userId, int pageNum);

    Cash createZeroCash(Long userId);
}

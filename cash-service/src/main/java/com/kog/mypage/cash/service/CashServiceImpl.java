package com.kog.mypage.cash.service;

import com.kog.mypage.cash.dto.AddCashDto;
import com.kog.mypage.cash.dto.UseCashDto;
import com.kog.mypage.cash.entity.AddCashRecord;
import com.kog.mypage.cash.repository.CashRepository;
import com.kog.mypage.cash.entity.Cash;
import com.kog.mypage.cash.entity.UseCashRecord;
import com.kog.mypage.cash.entity.enumerate.CashType;
import com.kog.mypage.cash.repository.CashRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CashServiceImpl implements CashService {
    private final CashRepository cashRepository;
    private final CashRecordRepository cashRecordRepository;

    public final int CASH_RECORD_PAGE_SIZE = 100;
    public final Sort CASH_RECORD_PAGE_SORT = Sort.by(Sort.Direction.ASC, "createdDate");

    @Transactional
    @Override
    public Cash getCashByUserId(Long userId) {
        Optional<Cash> optionalCash = cashRepository.findByUserId(userId);
        if(optionalCash.isPresent()){
            return optionalCash.orElseThrow();
        }
        else
            throw new RuntimeException("수정 필요");
    }

    @Transactional
    @Override
    public Cash useCash(UseCashDto cashDto) {
        cashRecordRepository.save(cashDto.createCashRecord());
        Cash cash = getCashByUserId(cashDto.getUserId());
        cash.changeCash(cashDto.getFreeAmount(), CashType.FREE_CASH);
        cash.changeCash(cashDto.getPaidAmount(), CashType.PAID_CASH);
        return cash;
    }

    @Transactional
    @Override
    public Cash addCash(AddCashDto cashDto) {
        cashRecordRepository.save(cashDto.createCashRecord());
        Cash cash = getCashByUserId(cashDto.getUserId());
        cash.changeCash(cashDto.getFreeAmount(), CashType.FREE_CASH);
        cash.changeCash(cashDto.getPaidAmount(), CashType.PAID_CASH);
        return cash;
    }

    @Transactional
    @Override
    public Cash createZeroCash(Long userId){
        Cash cash = Cash.builder()
                .userId(userId)
                .paidAmount(0).freeAmount(0)
                .build();
        return cashRepository.save(cash);
    }

    @Transactional
    @Override
    public Page<AddCashRecord> getAddCashRecordByUserId(Long userId, int pageNum) {
        PageRequest pageRequest = PageRequest.of(pageNum, CASH_RECORD_PAGE_SIZE, CASH_RECORD_PAGE_SORT);
        return cashRecordRepository.findAddCashRecord(userId, pageRequest);
    }

    @Transactional
    @Override
    public Page<UseCashRecord> getUseCashRecordByUserId(Long userId, int pageNum) {
        PageRequest pageRequest = PageRequest.of(pageNum, CASH_RECORD_PAGE_SIZE, CASH_RECORD_PAGE_SORT);
        return cashRecordRepository.findUseCashRecord(userId, pageRequest);
    }
}

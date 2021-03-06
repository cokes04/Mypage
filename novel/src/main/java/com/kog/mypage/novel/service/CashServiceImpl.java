package com.kog.mypage.novel.service;

import com.kog.mypage.novel.dto.AddCashDto;
import com.kog.mypage.novel.dto.UseCashDto;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.cash.Cash;
import com.kog.mypage.novel.entity.enumerate.CashType;
import com.kog.mypage.novel.entity.ticket.Ticket;
import com.kog.mypage.novel.repository.CashRecordRepository;
import com.kog.mypage.novel.repository.CashRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CashServiceImpl implements CashService {
    private final CashRepository cashRepository;
    private final CashRecordRepository cashRecordRepository;

    @Transactional(readOnly = true)
    @Override
    public Cash getCashByUser(User user) {
        Optional<Cash> optionalCash = cashRepository.findByUser(user);
        if(optionalCash.isPresent()){
            return optionalCash.orElseThrow();
        }
        else {
            Cash newCash = createZeroCash(user);
            return cashRepository.save(newCash);
        }
    }

    @Transactional
    @Override
    public Cash useCash(UseCashDto cashDto) {
        cashRecordRepository.save(cashDto.createCashRecord());
        Cash cash = getCashByUser(cashDto.getUser());
        cash.changeAmount(cashDto.getAmount(),cashDto.getCashType());
        return cash;
    }

    @Transactional
    @Override
    public Cash addCash(AddCashDto cashDto) {
        cashRecordRepository.save(cashDto.createCashRecord());
        Cash cash = getCashByUser(cashDto.getUser());
        cash.changeAmount(cashDto.getAmount(),cashDto.getCashType());
        return cash;
    }

    private Cash createZeroCash(User user){
        return Cash.builder().user(user).buy_amount(0).event_amount(0).build();
    }
}

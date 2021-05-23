package com.kog.mypage.cash.repository;

import com.kog.mypage.cash.entity.AddCashRecord;
import com.kog.mypage.cash.entity.CashRecord;
import com.kog.mypage.cash.entity.UseCashRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CashRecordRepository extends JpaRepository<CashRecord, Long> {

    @Query("SELECT acr FROM AddCashRecord as acr WHERE acr.userId = :userId")
    Page<AddCashRecord> findAddCashRecord(Long userId, Pageable pageable);

    @Query("SELECT ucr FROM UseCashRecord as ucr WHERE ucr.userId = :userId")
    Page<UseCashRecord> findUseCashRecord(Long userId, Pageable pageable);

}

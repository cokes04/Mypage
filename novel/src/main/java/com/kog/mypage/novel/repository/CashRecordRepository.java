package com.kog.mypage.novel.repository;

import com.kog.mypage.novel.entity.cash.CashRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashRecordRepository extends JpaRepository<CashRecord, Long> {
}

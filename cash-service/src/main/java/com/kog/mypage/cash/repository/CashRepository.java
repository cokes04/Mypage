package com.kog.mypage.cash.repository;

import com.kog.mypage.cash.entity.Cash;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CashRepository  extends JpaRepository<Cash, Long> {
    Optional<Cash> findByUserId(Long userId);
}

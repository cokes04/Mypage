package com.kog.mypage.novel.repository;

import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.cash.Cash;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CashRepository  extends JpaRepository<Cash, Long> {
    Optional<Cash> findByUser(User user);
}

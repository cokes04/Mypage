package com.kog.mypage.item.repository;

import com.kog.mypage.item.entity.Author;
import com.kog.mypage.item.entity.Novel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NovelRepository extends JpaRepository<Novel, Long> {
}

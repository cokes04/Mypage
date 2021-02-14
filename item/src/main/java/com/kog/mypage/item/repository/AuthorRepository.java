package com.kog.mypage.item.repository;

import com.kog.mypage.item.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}

package com.kog.mypage.novel.repository;

;
import com.kog.mypage.novel.entity.Novel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NovelRepository extends JpaRepository<Novel, Long> {

    Page<Novel> findAll(Pageable pageable);

    @Query("SELECT n FROM Novel as n where n.title LIKE %:word% AND n.openDate IS NOT NULL") // 작품명
    Page<Novel> serch(String word, Pageable pageable);

    boolean existsByTitle(String title);
}

package com.kog.mypage.novel.repository;

;
import com.kog.mypage.novel.entity.Author;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.enumerate.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface NovelRepository extends JpaRepository<Novel, Long> {
    Optional<Novel> findByTitle(String title);
    Page<Novel> findByGenre(Genre genre, Pageable pageable);
    Page<Novel> findAll(Pageable pageable);

    Page<Novel> findByTitleContaining(String word, Pageable pageable);

    @Query("SELECT n FROM Novel as n where n.author.name = :word or n.title = :word")
    Page<Novel> serch(String word, Pageable pageable);

    boolean existsByTitle(String title);
}

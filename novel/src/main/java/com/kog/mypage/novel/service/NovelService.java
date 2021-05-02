package com.kog.mypage.novel.service;

import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.enumerate.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NovelService {

    Optional<Novel>  getNovel(Long novelId);

    Optional<Novel> getNovelByTitle(String title);

    List<Novel> getNovelByAuthorName(String name);

    Page<Novel> getNovelByGenre(Genre genre, Pageable pageable);

    Page<Novel> getAllNovel(Pageable pageable);

    List<Novel> getAllNovel();

    Page<Novel> searchNovel(String word, Pageable pageable);

    Novel addNovel(Novel novel);

    Novel updateNovel(Novel novel);


    void deleteNovel(Novel novel);
}

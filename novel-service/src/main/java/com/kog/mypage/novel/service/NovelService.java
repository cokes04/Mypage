package com.kog.mypage.novel.service;

import com.kog.mypage.novel.dto.CreateNovelDto;
import com.kog.mypage.novel.dto.UpdateNovelDto;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.exception.NonExistentEntityException;
import com.kog.mypage.novel.exception.OverlapTitleException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NovelService {

    Novel getNovel(Long novelId)
            throws NonExistentEntityException;

    Page<Novel> searchNovel(String word, Pageable pageable);

    Novel createNovel(CreateNovelDto dto)
            throws OverlapTitleException;

    Novel createNovel(Novel novel);

    Novel updateNovel(Novel beforeNovel, UpdateNovelDto dto);

    void deleteNovel(Novel novel);

    void changeHidden(Novel novel, boolean hidden);
}

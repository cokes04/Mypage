package com.kog.mypage.novel.service;

import com.kog.mypage.novel.dto.CreateNovelDto;
import com.kog.mypage.novel.dto.UpdateNovelDto;
import com.kog.mypage.novel.dto.UserInfoDto;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.exception.InaccessibleEntityException;
import com.kog.mypage.novel.exception.NotFoundEntityException;
import com.kog.mypage.novel.exception.OverlapTitleException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NovelService {

    Novel getNovel(Long novelId, UserInfoDto userInfoDto)
            throws NotFoundEntityException, InaccessibleEntityException;

    Page<Novel> searchNovel(String word, Pageable pageable);

    Novel createNovel(CreateNovelDto dto, UserInfoDto userInfoDto)
            throws OverlapTitleException;

    Novel createNovel(Novel novel);

    Novel updateNovel(UpdateNovelDto dto, UserInfoDto userInfoDto)
            throws NotFoundEntityException, InaccessibleEntityException;

    Novel updateNovelofReleaseDate(Long novelId, UserInfoDto userInfoDto)
            throws NotFoundEntityException;

    void deleteNovel(Long novelId, UserInfoDto userInfoDto)
            throws NotFoundEntityException, InaccessibleEntityException;

    void changeHidden(Long novelId, boolean hidden, UserInfoDto userInfoDto)
            throws NotFoundEntityException, InaccessibleEntityException;
}

package com.kog.mypage.novel.service;

import com.kog.mypage.novel.dto.CreateEpisodeDto;
import com.kog.mypage.novel.dto.UpdateEpisodeDto;
import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.exception.NonExistentEntityException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EpisodeService {

    Episode getEpisode(Long episodeId)
            throws NonExistentEntityException;

    Page<Episode> getEpisodeOfNovelIncludeHidden(Long novelId, Pageable pageable);

    Page<Episode> getEpisodeOfNovelExcludeHidden(Long novelId, Pageable pageable);

    Episode createEpisode(Episode episode);

    Episode createEpisode(CreateEpisodeDto dto, Long userId);

    Episode updateEpisode(Episode beforeEpisode, UpdateEpisodeDto dto);

    void deleteEpisode(Episode episode);

    void deleteEpisodeOfNovel(Long novelId);

    boolean existEpisodeOfNovel(Long novelId);

    Episode changeHidden(Episode episode, boolean hidden);

    boolean isPurchase(Episode episode, Long userId);
}

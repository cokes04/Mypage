package com.kog.mypage.novel.service;

import com.kog.mypage.novel.entity.Episode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EpisodeService {

    Page<Episode> getEpisodeOfNovelId(Long novelId, Pageable pageable);
    Optional<Episode> getEpisode(Long episodeId);
}

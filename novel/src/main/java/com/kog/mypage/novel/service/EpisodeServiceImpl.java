package com.kog.mypage.novel.service;

import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.repository.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class EpisodeServiceImpl implements EpisodeService{

    private final EpisodeRepository episodeRepository;

    @Override
    public Page<Episode> getEpisodeOfNovelId(Long novelId, Pageable pageable) {
        return episodeRepository.findByNovel_Id(novelId, pageable);
    }

    @Override
    public Optional<Episode> getEpisode(Long episodeId) {
        return episodeRepository.findById(episodeId);
    }
}

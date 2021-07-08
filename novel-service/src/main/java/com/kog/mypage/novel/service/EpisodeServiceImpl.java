package com.kog.mypage.novel.service;

import com.kog.mypage.novel.dto.CreateEpisodeDto;
import com.kog.mypage.novel.dto.UpdateEpisodeDto;
import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.event.event.CreatedEpisodeEvent;
import com.kog.mypage.novel.event.event.DeletedEpisodeEvent;
import com.kog.mypage.novel.event.event.UpdatedEpisodeEvent;
import com.kog.mypage.novel.exception.NoPermissionException;
import com.kog.mypage.novel.exception.NonExistentEntityException;
import com.kog.mypage.novel.feign.TicketClient;
import com.kog.mypage.novel.repository.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
public class EpisodeServiceImpl implements EpisodeService{

    private final EpisodeRepository episodeRepository;
    private final NovelService novelService;
    private final ApplicationEventPublisher publisher;
    private final TicketClient ticketClient;

    @Override
    public Episode getEpisode(Long episodeId) {
        Episode episode = episodeRepository.findById(episodeId)
                .orElseThrow( () -> new NonExistentEntityException() );

        return episode;
    }

    @Override
    public Page<Episode> getEpisodeOfNovelIncludeHidden(Long novelId, Pageable pageable) {
        return episodeRepository.findByNovel_Id(novelId, pageable);
    }

    @Override
    public Page<Episode> getEpisodeOfNovelExcludeHidden(Long novelId, Pageable pageable) {
        return episodeRepository.findByNovel_IdAndOpenDateIsNotNull(novelId, pageable);
    }

    @Override
    public Episode createEpisode(Episode episode){ // 테스트용
        return episodeRepository.save(episode);
    }

    @Override
    public Episode createEpisode(CreateEpisodeDto dto, Long userId) {
        Novel novel = novelService.getNovel(dto.getNovelId());

        if (!novel.isOwner(userId))
            throw new NoPermissionException();

        int lastOrderValue = episodeRepository.countByNovel_Id(novel.getId());

        Episode episode = Episode.builder()
                .novel(novel)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .orderValue(lastOrderValue + 1)
                .content(dto.getContent())
                .openDate(dto.isHidden() ? null : LocalDateTime.now())
                .build();

        Episode saveEpisode = episodeRepository.save(episode);
        publisher.publishEvent(new CreatedEpisodeEvent());
        return saveEpisode;
    }

    @Override
    public Episode updateEpisode(Episode beforeEpisode, UpdateEpisodeDto dto) {
        Episode afterEpisode = Episode.builder()
                .id(beforeEpisode.getId())
                .novel(beforeEpisode.getNovel())
                .orderValue(beforeEpisode.getOrderValue())
                .openDate(beforeEpisode.getOpenDate())
                .createdDate(beforeEpisode.getCreatedDate())

                .title(dto.getTitle()
                        .orElse(beforeEpisode.getTitle()))
                .description(dto.getDescription()
                        .orElse(beforeEpisode.getDescription()))
                .content(dto.getContent()
                        .orElse(beforeEpisode.getContent()))
                .build();

        if (dto.getHidden().isPresent())
            afterEpisode.changeHidden(dto.getHidden().orElseThrow());

        Episode episode = episodeRepository.save(afterEpisode);
        publisher.publishEvent(new UpdatedEpisodeEvent());
        return episode;
    }

    @Override
    public void deleteEpisode(Episode episode) {
        episodeRepository.delete(episode);
        publisher.publishEvent(new DeletedEpisodeEvent());
    }

    @Override
    public void deleteEpisodeOfNovel(Long novelId) {
        episodeRepository.deleteByNovel_Id(novelId);
        publisher.publishEvent(new DeletedEpisodeEvent());
    }

    @Override
    public boolean existEpisodeOfNovel(Long novelId) {
        return episodeRepository.existsByNovel_Id(novelId);
    }

    @Override
    public Episode changeHidden(Episode episode, boolean hidden){
        episode.changeHidden(hidden);
        return episodeRepository.save(episode);
    }

    @Override
    public boolean isPurchase(Episode episode, Long userId) {
        return false;
    }
}

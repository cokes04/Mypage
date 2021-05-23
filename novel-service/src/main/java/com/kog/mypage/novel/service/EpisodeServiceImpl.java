package com.kog.mypage.novel.service;

import com.kog.mypage.novel.dto.CreateEpisodeDto;
import com.kog.mypage.novel.dto.UpdateEpisodeDto;
import com.kog.mypage.novel.dto.UserInfoDto;
import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.event.event.CreatedEpisodeEvent;
import com.kog.mypage.novel.event.event.DeletedEpisodeEvent;
import com.kog.mypage.novel.event.event.UpdatedEpisodeEvent;
import com.kog.mypage.novel.exception.InaccessibleEntityException;
import com.kog.mypage.novel.exception.NotFoundEntityException;
import com.kog.mypage.novel.repository.EpisodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Service
public class EpisodeServiceImpl implements EpisodeService{

    private final EpisodeRepository episodeRepository;
    private final NovelService novelService;
    private final ApplicationEventPublisher publisher;

    @Override
    public Episode getEpisode(Long episodeId, UserInfoDto userInfoDto) {
        Episode episode = episodeRepository.findById(episodeId)
                .orElseThrow( () -> new NotFoundEntityException() );

        if(episode.getNovel().isHidden() || episode.isHidden())
            checkAccessRights(episode, userInfoDto);

        return episode;
    }

    @Override
    public Page<Episode> getEpisodeOfNovel(Long novelId, Pageable pageable) {
        return episodeRepository.findByNovel_Id(novelId, pageable);
    }

    @Override
    public Episode createEpisode(Episode episode){ // 테스트용
        return episodeRepository.save(episode);
    }

    @Override
    public Episode createEpisode(CreateEpisodeDto dto, UserInfoDto userInfoDto) {
        Novel novel = novelService.getNovel(dto.getNovelId(), userInfoDto);

        Episode episode = Episode.builder()
                .novel(novel)
                .title(dto.getTitle())
                .description(dto.getDescription())
                .content(dto.getContent())
                .openDate(dto.isHidden() ? null : LocalDateTime.now())
                .build();

        Episode saveEpisode = episodeRepository.save(episode);
        publisher.publishEvent(new CreatedEpisodeEvent());
        return saveEpisode;
    }

    @Override
    public Episode updateEpisode(UpdateEpisodeDto dto, UserInfoDto userInfoDto) {
        Episode beforeEpisode = episodeRepository.findById(dto.getEpisodeId())
                .orElseThrow( () -> new NotFoundEntityException() );

        checkAccessRights(beforeEpisode, userInfoDto);

        Episode afterEpisode = Episode.builder()
                .id(beforeEpisode.getId())
                .novel(beforeEpisode.getNovel())
                .round(beforeEpisode.getRound())
                .openDate(beforeEpisode.getOpenDate())
                .createDate(beforeEpisode.getCreateDate())

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
    public void deleteEpisode(Long episodeId, UserInfoDto userInfoDto) {
        Episode episode = episodeRepository.findById(episodeId)
                .orElseThrow( () -> new NotFoundEntityException() );

        checkAccessRights(episode, userInfoDto);

        episodeRepository.delete(episode);
        publisher.publishEvent(new DeletedEpisodeEvent());
    }

    @Override
    public void deleteEpisodeOfNovel(Long novelId, UserInfoDto userInfoDto) {
        Novel novel = novelService.getNovel(novelId, userInfoDto);

        episodeRepository.deleteByNovel(novel);

        publisher.publishEvent(new DeletedEpisodeEvent());
    }

    @Override
    public boolean existEpisodeOfNovel(Long novelId) {
        return episodeRepository.existsByNovel_Id(novelId);
    }

    @Override
    public Episode changeHidden(Long episodeId, UserInfoDto userInfoDto, boolean hidden){
        Episode episode = episodeRepository.findById(episodeId)
                .orElseThrow( () -> new NotFoundEntityException() );

        checkAccessRights(episode, userInfoDto);

        episode.changeHidden(hidden);

        return episodeRepository.save(episode);
    }

    private void checkAccessRights(Episode episode, UserInfoDto userInfoDto){
        // admin이거나 해당 novel을 가진 user일때만
        if(userInfoDto.getRoles().contains("ADMIN"))
            return;

        if (episode.getNovel().getUserId() == userInfoDto.getUserId())
            return;

        throw new InaccessibleEntityException();
    }
}

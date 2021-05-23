package com.kog.mypage.novel.service;


import com.kog.mypage.novel.dto.CreateNovelDto;
import com.kog.mypage.novel.dto.UpdateNovelDto;
import com.kog.mypage.novel.dto.UserInfoDto;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.event.event.CreatedNovelEvent;
import com.kog.mypage.novel.event.event.DeletedNovelEvent;
import com.kog.mypage.novel.event.event.UpdatedNovelEvent;
import com.kog.mypage.novel.exception.InaccessibleEntityException;
import com.kog.mypage.novel.exception.NotFoundEntityException;
import com.kog.mypage.novel.exception.OverlapTitleException;
import com.kog.mypage.novel.repository.NovelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
public class NovelServiceImpl implements NovelService {

    private final NovelRepository novelRepository;
    private final ApplicationEventPublisher publisher;

    @Override
    public Novel getNovel(Long novelId, UserInfoDto userInfoDto) {
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow( () -> new NotFoundEntityException() );

        if(novel.isHidden())
            checkAccessRights(novel, userInfoDto);

        return novel;
    }

    @Override
    public Page<Novel> searchNovel(String word, Pageable pageable) {
        return novelRepository.serch(word, pageable);
    }


    @Override
    public Novel createNovel(Novel novel) { //test용
        Novel saveNovel = novelRepository.save(novel);
        publisher.publishEvent(new CreatedNovelEvent());
        return saveNovel;
    }

    @Override
    public Novel createNovel(CreateNovelDto dto, UserInfoDto userInfoDto){
        Novel novel = Novel.builder()
                .userId(userInfoDto.getUserId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .ageGrade(dto.getAgeGrade())
                .genre(dto.getGenre())
                .serialCycle(dto.getSerialCycle())
                .openDate(dto.isHidden() ? null : LocalDateTime.now())
                .build();

        try {
            Novel saveNovel = novelRepository.save(novel);
            publisher.publishEvent(new CreatedNovelEvent());
            return saveNovel;
        }catch (RuntimeException e){ // 수정 필요
            System.out.println(e.getMessage());
            throw new OverlapTitleException();
        }
    }

    @Override
    public Novel updateNovel(UpdateNovelDto dto, UserInfoDto userInfoDto){
        Novel beforeNovel = novelRepository.findById(dto.getNovelId())
                .orElseThrow( () -> new NotFoundEntityException() );

        checkAccessRights(beforeNovel, userInfoDto);

        Novel afterNovel = Novel.builder()
                .id(beforeNovel.getId())
                .userId(beforeNovel.getUserId())
                .freeEpisodeCount(beforeNovel.getFreeEpisodeCount())
                .publisher(beforeNovel.getPublisher())
                .pricePerEpisode(beforeNovel.getPricePerEpisode())
                .createdDate(beforeNovel.getCreatedDate())
                .openDate(beforeNovel.getOpenDate())
                .lastReleaseDate(beforeNovel.getLastReleaseDate())

                .title(dto.getTitle()
                        .orElse(beforeNovel.getTitle()))
                .description(dto.getDescription()
                        .orElse(beforeNovel.getDescription()))
                .ageGrade( dto.getAgeGrade()
                        .orElse(beforeNovel.getAgeGrade()))
                .genre( dto.getGenre()
                        .orElse(beforeNovel.getGenre()))
                .serialCycle(dto.getSerialCycle()
                        .orElse(beforeNovel.getSerialCycle()))
                .build();

        if (dto.getHidden().isPresent())
            afterNovel.changeHidden(dto.getHidden().orElseThrow());

        Novel updateNovel = novelRepository.save(afterNovel);
        publisher.publishEvent(new UpdatedNovelEvent());
        return updateNovel;
    }

    @Override
    public Novel updateNovelofReleaseDate(Long novelId, UserInfoDto userInfoDto){
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow( () -> new NotFoundEntityException() );

        checkAccessRights(novel, userInfoDto);

        novel.updateReleaseDate();

        Novel updateNovel = novelRepository.save(novel);
        return updateNovel;
    }

    @Override
    public void deleteNovel(Long novelId, UserInfoDto userInfoDto) {
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow( () -> new NotFoundEntityException());

        checkAccessRights(novel, userInfoDto);

        novelRepository.deleteById(novelId);
        publisher.publishEvent(new DeletedNovelEvent());
    }

    @Override
    public void changeHidden(Long novelId, boolean hidden, UserInfoDto userInfoDto) {
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow( () -> new NotFoundEntityException());

        checkAccessRights(novel, userInfoDto);

        novel.changeHidden(hidden);

        novelRepository.save(novel);
    }

    private void checkAccessRights(Novel novel, UserInfoDto userInfoDto){
        // admin이거나 해당 novel을 가진 user일때만
        if(userInfoDto.getRoles().contains("ADMIN"))
            return;

        if (novel.getUserId() == userInfoDto.getUserId())
            return;

        throw new InaccessibleEntityException();
    }
}

package com.kog.mypage.novel.service;


import com.kog.mypage.novel.dto.CreateNovelDto;
import com.kog.mypage.novel.dto.UpdateNovelDto;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.event.event.CreatedNovelEvent;
import com.kog.mypage.novel.event.event.DeletedNovelEvent;
import com.kog.mypage.novel.event.event.UpdatedNovelEvent;
import com.kog.mypage.novel.exception.NonExistentEntityException;
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
    public Novel getNovel(Long novelId) {
        Novel novel = novelRepository.findById(novelId)
                .orElseThrow( () -> new NonExistentEntityException() );
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
    public Novel createNovel(CreateNovelDto dto){
        Novel novel = Novel.builder()
                .userId(dto.getUserId())
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
    public Novel updateNovel(Novel beforeNovel, UpdateNovelDto dto){
        Novel afterNovel = Novel.builder()
                .id(beforeNovel.getId())
                .userId(beforeNovel.getUserId())
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
    public void deleteNovel(Novel novel) {
        novelRepository.delete(novel);
        publisher.publishEvent(new DeletedNovelEvent(novel.getId(), novel.getUserId()));
    }

    @Override
    public void changeHidden(Novel novel, boolean hidden) {
        novel.changeHidden(hidden);
        novelRepository.save(novel);
    }
}

package com.kog.mypage.novel.controller;

import com.kog.mypage.novel.dto.CreateEpisodeDto;
import com.kog.mypage.novel.dto.UpdateEpisodeDto;
import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.enumeration.NovelSort;
import com.kog.mypage.novel.exception.client.BadRequestException;
import com.kog.mypage.novel.exception.client.ForbiddenException;
import com.kog.mypage.novel.payload.request.CreateEpisodeRequest;
import com.kog.mypage.novel.payload.request.EpisodePageRequest;
import com.kog.mypage.novel.payload.request.UpdateEpisodeRequest;
import com.kog.mypage.novel.payload.request.UserInfo;
import com.kog.mypage.novel.payload.response.*;
import com.kog.mypage.novel.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/episode")
public class EpisodeController {

    private final EpisodeService episodeService;

    @GetMapping("/information")
    public ResponseEntity<?> getEpisodeInformationsInOfNovel(@Validated @RequestParam("novelId") Long novelId,
                                                             @Validated EpisodePageRequest pageRequest){
        int page = pageRequest.getPage();
        int size = pageRequest.getSize();
        Sort sort = NovelSort.valueOf(pageRequest.getStandard().toUpperCase()).getSort();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Episode> episodes = episodeService.getEpisodeOfNovelExcludeHidden(novelId, pageable);

        return ResponseEntity.ok()
                .body(EpisodeInformationListResponse.of(true, "성공", episodes));
    }

    @GetMapping("/{episodeId}/information")
    public ResponseEntity<?> getEpisodeInformation(@Validated @PathVariable("episodeId") Long episodeId,
                                                   @Validated UserInfo userInfo){
        Long userId = userInfo.getUserId();
        Episode episode = episodeService.getEpisode(episodeId);

        // 비공개 && 주인아님
        if (episode.isHidden() && !episode.isOwner(userId))
            throw new ForbiddenException();

        // 공개 || 비공개 && 주인
        return ResponseEntity.ok()
                .body(EpisodeInformationResponse.of(true, "성공", episode));
    }

    @GetMapping("/{episodeId}/content")
    public ResponseEntity<?> getEpisodeContent(@Validated @PathVariable("episodeId") Long episodeId,
                                               @Validated UserInfo userInfo){

        Long userId = userInfo.getUserId();
        Episode episode = episodeService.getEpisode(episodeId);

        // 공개 && (구매안함 || 무료아님 || 주인아님)  || 비공개 && 주인아님
        if ( !episode.isHidden() && !(episodeService.isPurchase(episode, userId) || episode.isFree() || episode.isOwner(userId))
        || episode.isHidden() && !episode.isOwner(userId))
            throw new ForbiddenException();

        // 공개 && (구매 || 무료 || 주인) || 비공개 && 주인
        return ResponseEntity.ok()
                .body(EpisodeContentResponse.of(true, "성공", episode));
    }

    @PostMapping
    public ResponseEntity<?> createEpisode(@Validated @RequestBody CreateEpisodeRequest request){
        Long userId = request.getUserInfo().getUserId();

        CreateEpisodeDto createEpisodeDto;
        try {
            createEpisodeDto = CreateEpisodeDto.builder()
                    .novelId(request.getNovelId())
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .content(request.getContent())
                    .hidden(request.isHidden())
                    .build();
        }catch (IllegalArgumentException e){
            throw new BadRequestException();
        }

        Episode episode = episodeService.createEpisode(createEpisodeDto, userId);
        return ResponseEntity
                .created(URI.create("/episode/" + episode.getId()))
                .build();
    }

    @PutMapping
    public ResponseEntity<?> updateEpisode(@Validated @RequestBody UpdateEpisodeRequest request){
        Long userId = request.getUserInfo().getUserId();

        UpdateEpisodeDto updateEpisodeDto;
        try {
            updateEpisodeDto = UpdateEpisodeDto.builder()
                    .episodeId(request.getEpisodeId())
                    .title(request.getTitle())
                    .description(request.getDescription())
                    .content(request.getContent())
                    .hidden(request.getHidden())
                    .build();
        }catch (IllegalArgumentException e){
            throw new BadRequestException();
        }

        Episode beforeEpisode = episodeService.getEpisode(request.getEpisodeId());

        Episode episode;
        if (beforeEpisode.isOwner(userId))
            episode = episodeService.updateEpisode(beforeEpisode, updateEpisodeDto);
        else
            throw new ForbiddenException();

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{episodeId}")
    public ResponseEntity<?> deleteEpisode(@Validated @PathVariable("episodeId") Long episodeId,
                                           @Validated UserInfo userInfo){
        Long userId = userInfo.getUserId();

        Episode episode = episodeService.getEpisode(episodeId);

        if (episode.isOwner(userId))
            episodeService.deleteEpisode(episode);
        else
            throw new ForbiddenException();

        return ResponseEntity.noContent().build();
    }
}

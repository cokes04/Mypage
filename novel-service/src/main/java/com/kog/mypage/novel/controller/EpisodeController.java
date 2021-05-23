package com.kog.mypage.novel.controller;

import com.kog.mypage.novel.dto.CreateEpisodeDto;
import com.kog.mypage.novel.dto.UpdateEpisodeDto;
import com.kog.mypage.novel.dto.UserInfoDto;
import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.enumerate.EpisodeSort;
import com.kog.mypage.novel.payload.request.CreateEpisodeRequest;
import com.kog.mypage.novel.payload.request.EpisodePageRequest;
import com.kog.mypage.novel.payload.request.UpdateEpisodeRequest;
import com.kog.mypage.novel.payload.request.UserInfo;
import com.kog.mypage.novel.payload.response.ApiResponse;
import com.kog.mypage.novel.payload.response.EpisodeContentResponse;
import com.kog.mypage.novel.payload.response.EpisodeInformationListResponse;
import com.kog.mypage.novel.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/episode")
public class EpisodeController {

    private final EpisodeService episodeService;

    @GetMapping                             //     /episode?novelid=300&page=0&size=50&strandard
    public ResponseEntity<?> getEpisodeInformationsOfNovel(@Validated @RequestParam("novelId") Long novelId,
                                                           @Validated EpisodePageRequest pageRequest){

        int page = pageRequest.getPage();
        int size = pageRequest.getSize();
        Sort sort = EpisodeSort.valueOf(pageRequest.getStrandard().toUpperCase()).getSort();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Episode> episodes = episodeService.getEpisodeOfNovel(novelId, pageable);

        EpisodeInformationListResponse episodeInformationListResponse = EpisodeInformationListResponse.of(true, "성공", episodes);
        return ResponseEntity.ok().body(episodeInformationListResponse);
    }


    @GetMapping("/{episodeId}")     //  /episode/10
    public ResponseEntity<?> getContentOfEpisode(@Validated @PathVariable("episodeId") Long episodeId,
                                                 @Validated UserInfo userInfo){

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(userInfo.getUserId())
                .roles(userInfo.getRoles())
                .build();

        Episode episode = episodeService.getEpisode(episodeId, userInfoDto);

            return ResponseEntity.ok()
                    .body(EpisodeContentResponse.of(true, "성공", episode)); // 수정필요
    }


    @PostMapping
    public ResponseEntity<?> createEpisode(@Validated @RequestBody CreateEpisodeRequest request){

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(request.getUserInfo().getUserId())
                .roles(request.getUserInfo().getRoles())
                .build();

        CreateEpisodeDto createEpisodeDto = CreateEpisodeDto.builder()
                .novelId(request.getNovelId())
                .title(request.getTitle())
                .description(request.getDescription())
                .content(request.getContent())
                .hidden(request.isHidden())
                .build();

        Episode episode = episodeService.createEpisode(createEpisodeDto, userInfoDto);
        return ResponseEntity.ok().body(new ApiResponse(true, "성공"));
    }

    @PutMapping
    public ResponseEntity<?> updateEpisode(@Validated @RequestBody UpdateEpisodeRequest request){

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(request.getUserInfo().getUserId())
                .roles(request.getUserInfo().getRoles())
                .build();

        UpdateEpisodeDto updateEpisodeDto = UpdateEpisodeDto.builder()
                .episodeId(request.getEpisodeId())
                .title(request.getTitle())
                .description(request.getDescription())
                .content(request.getContent())
                .hidden(request.getHidden())
                .build();

        Episode episode = episodeService.updateEpisode(updateEpisodeDto, userInfoDto);
        return ResponseEntity.ok().body(new ApiResponse(true, "성공"));

    }

    @DeleteMapping
    public ResponseEntity<?> deleteEpisode(@Validated @PathVariable("episodeId") Long episodeId,
                                           @Validated UserInfo userInfo){

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(userInfo.getUserId())
                .roles(userInfo.getRoles())
                .build();

        episodeService.deleteEpisode(episodeId, userInfoDto);

        return ResponseEntity.ok()
                .body(new ApiResponse(true, "성공"));

    }
}

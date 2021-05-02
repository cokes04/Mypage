package com.kog.mypage.novel.controller;

import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.payload.request.EpisodeRequest;
import com.kog.mypage.novel.payload.response.ApiResponse;
import com.kog.mypage.novel.payload.response.EpisodeContentResponse;
import com.kog.mypage.novel.payload.response.EpisodeInformationListResponse;
import com.kog.mypage.novel.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/episode")
public class EpisodeController {

    private final EpisodeService episodeService;

    @PostMapping
    public ResponseEntity<?> getEpisodesOfNovel(@Validated @RequestBody EpisodeRequest episodeRequest){
        Long novelId = episodeRequest.getNovelId();
        Pageable pageable = episodeRequest.getPageble();

        Page<Episode> episodes = episodeService.getEpisodeOfNovelId(novelId, pageable);

        EpisodeInformationListResponse episodeInformationListResponse = EpisodeInformationListResponse.of(true, "성공", episodes);
        return ResponseEntity.ok().body(episodeInformationListResponse);
    }


    @PostMapping("/content")
    public ResponseEntity<?> getContentOfEpisode(@Validated @RequestBody Map<String,Long> map){
        Long episodeId = map.get("episodeId");

        Optional<Episode> optionalEpisode = episodeService.getEpisode(episodeId);
        if(optionalEpisode.isPresent()){
            Episode episode = optionalEpisode.orElseThrow();
            return ResponseEntity.ok().body(EpisodeContentResponse.of(episode));
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "실패"));
        }
    }
}

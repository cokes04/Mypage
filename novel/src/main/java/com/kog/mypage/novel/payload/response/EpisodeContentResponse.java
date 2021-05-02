package com.kog.mypage.novel.payload.response;

import com.kog.mypage.novel.entity.Episode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EpisodeContentResponse {

    String content;

    @Builder
    public EpisodeContentResponse(String content) {
        this.content = content;
    }

    public static EpisodeContentResponse of(Episode episode){
        return EpisodeContentResponse.builder()
                .content(episode.getContent())
                .build();
    }
}

package com.kog.mypage.novel.payload.response;

import com.kog.mypage.novel.entity.Episode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EpisodeContentResponse extends ApiResponse {

    String content;

    @Builder
    private EpisodeContentResponse(boolean success, String message, String content) {
        super(success, message);
        this.content = content;
    }

    public static EpisodeContentResponse of(boolean success, String message, Episode episode){
        return EpisodeContentResponse.builder()
                .success(success)
                .message(message)
                .content(episode.getContent())
                .build();
    }
}

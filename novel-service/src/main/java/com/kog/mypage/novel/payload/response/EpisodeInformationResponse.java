package com.kog.mypage.novel.payload.response;

import com.kog.mypage.novel.entity.Episode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class EpisodeInformationResponse extends  ApiResponse {

    private Long episodeId;

    private int orderValue;

    private String title;

    private String description;

    private String hidden;

    @Builder
    public EpisodeInformationResponse(boolean success, String message, Long episodeId, int orderValue, String title, String description, String hidden) {
        super(success, message);
        this.episodeId = episodeId;
        this.orderValue = orderValue;
        this.title = title;
        this.description = description;
        this.hidden = hidden;
    }

    public static EpisodeInformationResponse of(boolean success, String message, Episode episode){
        return EpisodeInformationResponse.builder()
                .success(success)
                .message(message)
                .episodeId(episode.getId())
                .orderValue(episode.getOrderValue())
                .title(episode.getTitle())
                .description(episode.getDescription())
                .hidden(episode.isHidden() ? "Y" : "N")
                .build();

    }
}

package com.kog.mypage.novel.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kog.mypage.novel.entity.Episode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class EpisodeInformationListResponse extends ApiResponse{

   private List<EpisodeInformationResponse> episodeInformationResponses;

   private Long totalEpisodeCount;

   private int totalEpisodePage;

   @Builder
   private EpisodeInformationListResponse(boolean success, String message, List<EpisodeInformationResponse> episodeInformationResponses,
                                          Long totalEpisodeCount, int totalEpisodePage) {
        super(success, message);
        this.episodeInformationResponses = episodeInformationResponses;
        this.totalEpisodeCount = totalEpisodeCount;
        this.totalEpisodePage = totalEpisodePage;
    }

    public static EpisodeInformationListResponse of(boolean success, String message, Page<Episode> episodeResponses){
       return EpisodeInformationListResponse.builder()
               .success(success)
               .message(message)
               .episodeInformationResponses(episodeResponses.map(EpisodeInformationResponse::new).toList())
               .totalEpisodeCount(episodeResponses.getTotalElements())
               .totalEpisodePage(episodeResponses.getTotalPages())
               .build();
    }

    @Getter
    private static class EpisodeInformationResponse{

        private Long episodeId;

        private int round;

        private String title;

        private String description;

        private String hidden;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createDate;

        public EpisodeInformationResponse(Episode episode) {
            this.episodeId = episode.getId();
            this.round = episode.getRound();
            this.title = episode.getTitle();
            this.description = episode.getDescription();
            this.createDate = episode.getCreateDate();
            this.hidden = episode.getOpenDate().equals(null) ? "Y" : "N";
        }
    }
}

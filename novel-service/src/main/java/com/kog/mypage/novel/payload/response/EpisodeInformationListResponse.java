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

   private List<EpisodeInformationResponse> episodeInformationList;

   private Long totalEpisodeCount;

   private int totalEpisodePage;

   @Builder
   private EpisodeInformationListResponse(boolean success, String message, List<EpisodeInformationResponse> episodeInformationList,
                                          Long totalEpisodeCount, int totalEpisodePage) {
        super(success, message);
        this.episodeInformationList = episodeInformationList;
        this.totalEpisodeCount = totalEpisodeCount;
        this.totalEpisodePage = totalEpisodePage;
    }

    public static EpisodeInformationListResponse of(boolean success, String message, Page<Episode> episodeResponses){
       return EpisodeInformationListResponse.builder()
               .success(success)
               .message(message)
               .episodeInformationList(episodeResponses.map(EpisodeInformationResponse::of).toList())
               .totalEpisodeCount(episodeResponses.getTotalElements())
               .totalEpisodePage(episodeResponses.getTotalPages())
               .build();
    }

    @Getter
    private static class EpisodeInformationResponse{

        private Long episodeId;

        private int orderValue;

        private String title;

        private String description;

        private String hidden;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdDate;

        @Builder
        public EpisodeInformationResponse(Long episodeId, int orderValue, String title, String description, String hidden, LocalDateTime createdDate) {
            this.episodeId = episodeId;
            this.orderValue = orderValue;
            this.title = title;
            this.description = description;
            this.hidden = hidden;
            this.createdDate = createdDate;
        }

        public static EpisodeInformationResponse of(Episode episode){
            return EpisodeInformationResponse.builder()
                    .episodeId(episode.getId())
                    .orderValue(episode.getOrderValue())
                    .title(episode.getTitle())
                    .description(episode.getDescription())
                    .createdDate(episode.getCreatedDate())
                    .hidden(episode.getOpenDate().equals(null) ? "Y" : "N")
                    .build();
        }
    }
}

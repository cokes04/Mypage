package com.kog.mypage.novel.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.kog.mypage.novel.entity.Novel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class NovelSearchResponse extends ApiResponse {

    private List<NovelResponse> novels;

    @Builder
    private NovelSearchResponse(boolean success, String message, List<NovelResponse> novels) {
        super(success, message);
        this.novels = novels;
    }

    public static NovelSearchResponse of(boolean success, String message, Page<Novel> novels){
        return NovelSearchResponse.builder()
                .success(success)
                .message(message)
                .novels(novels.map(NovelResponse::of).toList())
        .build();
    }

    @Getter
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private static class NovelResponse{
        private Long id;

        private String authorName;

        private String title;

        private int pricePerEpisode;

        private int ageGrade;

        private List<String> genre;

        private List<String> serialCycle;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdDate;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime openDate;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime lastReleaseDate;

        @Builder
        private NovelResponse(Long id, String authorName, String title, int pricePerEpisode,
                             int ageGrade, List<String> genre, List<String> serialCycle,
                             LocalDateTime createdDate, LocalDateTime openDate, LocalDateTime lastReleaseDate) {
            this.id = id;
            this.authorName = authorName;
            this.title = title;
            this.pricePerEpisode = pricePerEpisode;
            this.ageGrade = ageGrade;
            this.genre = genre;
            this.serialCycle = serialCycle;
            this.createdDate = createdDate;
            this.openDate = openDate;
            this.lastReleaseDate = lastReleaseDate;
        }


        private static NovelResponse of(Novel novel){
            return NovelResponse.builder()
                    .id(novel.getId())
                    .title(novel.getTitle())
                    .pricePerEpisode(novel.getPricePerEpisode())
                    .ageGrade(novel.getAgeGrade().value)
                    .genre(novel.getGenre().stream().map(i -> i.value).collect(Collectors.toList()))
                    .serialCycle(novel.getSerialCycle().stream().map(i -> i.value).collect(Collectors.toList()))
                    .createdDate(novel.getCreatedDate())
                    .openDate(novel.getOpenDate())
                    .lastReleaseDate(novel.getLastReleaseDate())
                    .build();
        }
    }
}

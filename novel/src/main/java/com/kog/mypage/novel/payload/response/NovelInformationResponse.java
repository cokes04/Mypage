package com.kog.mypage.novel.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kog.mypage.novel.entity.Novel;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class NovelInformationResponse extends ApiResponse{

    private Long id;

    private Long authorId;

    private String authorName;

    private String title;

    private String description;

    private int freeEpisodeCount;

    private int pricePerEpisode;

    private int ageGrade;

    private String genre;

    private List<String> serialCycle;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startSaleDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastReleaseDate;


    @Builder
    private NovelInformationResponse(boolean success, String message, Long id, Long authorId, String authorName, String title, String description,
                             int freeEpisodeCount, int pricePerEpisode, int ageGrade, String genre, List<String> serialCycle,
                             LocalDateTime createdDate, LocalDateTime startSaleDate, LocalDateTime lastReleaseDate) {
        super(success, message);
        this.id = id;
        this.authorId = authorId;
        this.authorName = authorName;
        this.title = title;
        this.description = description;
        this.freeEpisodeCount = freeEpisodeCount;
        this.pricePerEpisode = pricePerEpisode;
        this.ageGrade = ageGrade;
        this.genre = genre;
        this.serialCycle = serialCycle;
        this.createdDate = createdDate;
        this.startSaleDate = startSaleDate;
        this.lastReleaseDate = lastReleaseDate;
    }

    public static NovelInformationResponse of(boolean success, String message, Novel novel){
        return NovelInformationResponse.builder()
                .success(success)
                .message(message)
                .id(novel.getId())
                .authorId(novel.getAuthor().getId())
                .authorName(novel.getAuthor().getName())
                .title(novel.getTitle())
                .description(novel.getDescription())
                .freeEpisodeCount(novel.getFreeEpisodeCount())
                .pricePerEpisode(novel.getPricePerEpisode())
                .ageGrade(novel.getAgeGrade().value)
                .genre(novel.getGenre().value)
                .serialCycle(novel.getSerialCycle().stream().map(i -> i.value).collect(Collectors.toList()))
                .createdDate(novel.getCreatedDate())
                .startSaleDate(novel.getStartSaleDate())
                .lastReleaseDate(novel.getLastReleaseDate())
                .build();
    }
}

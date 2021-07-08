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

    private String title;

    private String description;

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
    private NovelInformationResponse(boolean success, String message, Long id, String title, String description,
                                     int pricePerEpisode, int ageGrade, List<String> genre, List<String> serialCycle,
                                     LocalDateTime createdDate, LocalDateTime openDate, LocalDateTime lastReleaseDate) {
        super(success, message);
        this.id = id;
        this.title = title;
        this.description = description;
        this.pricePerEpisode = pricePerEpisode;
        this.ageGrade = ageGrade;
        this.genre = genre;
        this.serialCycle = serialCycle;
        this.createdDate = createdDate;
        this.openDate = openDate;
        this.lastReleaseDate = lastReleaseDate;
    }

    public static NovelInformationResponse of(boolean success, String message, Novel novel){
        return NovelInformationResponse.builder()
                .success(success)
                .message(message)
                .id(novel.getId())
                .title(novel.getTitle())
                .description(novel.getDescription())
                .pricePerEpisode(novel.getPricePerEpisode())
                .ageGrade(novel.getAgeGrade().getValue())
                .genre(novel.getGenre().stream().map(i -> i.getValue()).collect(Collectors.toList()))
                .serialCycle(novel.getSerialCycle().stream().map(i -> i.getValue()).collect(Collectors.toList()))
                .createdDate(novel.getCreatedDate())
                .openDate(novel.getOpenDate())
                .lastReleaseDate(novel.getLastReleaseDate())
                .build();
    }
}

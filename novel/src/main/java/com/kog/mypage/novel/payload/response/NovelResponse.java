package com.kog.mypage.novel.payload.response;

import com.kog.mypage.novel.entity.Author;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.enumerate.AgeGrade;
import com.kog.mypage.novel.entity.enumerate.Genre;
import com.kog.mypage.novel.entity.enumerate.SerialCycle;
import com.kog.mypage.novel.entity.enumerate.SerialCycleConverter;
import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class NovelResponse {

    private AuthorResponse author;

    private String title;

    private int pricePerEpisode;

    private AgeGrade agetGrade;

    private Genre genre;

    private List<SerialCycle> serialCycle;

    private LocalDateTime createdDate;

    @Builder
    private NovelResponse(AuthorResponse author, String title, int pricePerEpisode, AgeGrade agetGrade, Genre genre, List<SerialCycle> serialCycle, LocalDateTime createdDate) {
        this.author = author;
        this.title = title;
        this.pricePerEpisode = pricePerEpisode;
        this.agetGrade = agetGrade;
        this.genre = genre;
        this.serialCycle = serialCycle;
        this.createdDate = createdDate;
    }

    public static NovelResponse of(Novel novel){
        return NovelResponse.builder()
                .author(AuthorResponse.of(novel.getAuthor()))
                .title(novel.getTitle())
                .pricePerEpisode(novel.getPricePerEpisode())
                .agetGrade(novel.getAgetGrade())
                .genre(novel.getGenre())
                .serialCycle(novel.getSerialCycle())
                .createdDate(novel.getCreatedDate())
                .build();

    }
}

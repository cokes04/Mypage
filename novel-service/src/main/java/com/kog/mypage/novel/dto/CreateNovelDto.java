package com.kog.mypage.novel.dto;

import com.kog.mypage.novel.enumerate.AgeGrade;
import com.kog.mypage.novel.enumerate.Genre;
import com.kog.mypage.novel.enumerate.SerialCycle;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateNovelDto {

    private String title;

    private String description;

    private AgeGrade ageGrade;

    private List<Genre> genre;

    private List<SerialCycle> serialCycle;

    private boolean hidden;

    @Builder
    public CreateNovelDto(String title, String description, AgeGrade ageGrade, List<Genre> genre, List<SerialCycle> serialCycle, boolean hidden) {
        this.title = title;
        this.description = description;
        this.ageGrade = ageGrade;
        this.genre = genre;
        this.serialCycle = serialCycle;
        this.hidden = hidden;
    }
}

package com.kog.mypage.novel.dto;

import com.kog.mypage.novel.enumeration.AgeGrade;
import com.kog.mypage.novel.enumeration.Genre;
import com.kog.mypage.novel.enumeration.SerialCycle;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Optional;


@Getter
@ToString
public class UpdateNovelDto {

    private Long userId;

    private Long novelId;

    private Optional<String> title;

    private Optional<String> description;

    private Optional<AgeGrade> ageGrade;

    private Optional<List<Genre>> genre;

    private Optional<List<SerialCycle>> serialCycle;

    private Optional<Boolean> hidden;

    @Builder
    public UpdateNovelDto(Long userId, Long novelId, Optional<String> title, Optional<String> description, Optional<AgeGrade> ageGrade,
                          Optional<List<Genre>> genre, Optional<List<SerialCycle>> serialCycle, Optional<Boolean> hidden) {
        this.userId = userId;
        this.novelId = novelId;
        this.title = title;
        this.description = description;
        this.ageGrade = ageGrade;
        this.genre = genre;
        this.serialCycle = serialCycle;
        this.hidden = hidden;
    }
}

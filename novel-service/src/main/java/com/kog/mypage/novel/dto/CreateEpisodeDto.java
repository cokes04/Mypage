package com.kog.mypage.novel.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateEpisodeDto {


    private Long novelId;

    private String title;

    private String description;

    private String content;

    private boolean hidden;


    @Builder
    public CreateEpisodeDto(Long novelId, String title, String description, String content, boolean hidden) {
        this.novelId = novelId;
        this.title = title;
        this.description = description;
        this.content = content;
        this.hidden = hidden;
    }
}

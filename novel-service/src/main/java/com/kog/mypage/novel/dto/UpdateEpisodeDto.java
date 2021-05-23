package com.kog.mypage.novel.dto;

import com.kog.mypage.novel.entity.Novel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
public class UpdateEpisodeDto {

    private Long episodeId;

    private Optional<String> title;

    private Optional<String> description;

    private Optional<String> content;

    private Optional<Boolean> hidden;


    @Builder
    public UpdateEpisodeDto(Long episodeId, Optional<String> title, Optional<String> description, Optional<String> content, Optional<Boolean> hidden) {
        this.episodeId = episodeId;
        this.title = title;
        this.description = description;
        this.content = content;
        this.hidden = hidden;
    }


}

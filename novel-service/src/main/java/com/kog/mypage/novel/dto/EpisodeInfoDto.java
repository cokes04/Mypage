package com.kog.mypage.novel.dto;

import com.kog.mypage.novel.converter.BooleanToYNConverter;
import com.kog.mypage.novel.converter.LocalDateTimeConverter;
import com.kog.mypage.novel.entity.Novel;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
public class EpisodeInfoDto {

    private Long id;

    private Novel novel;

    private int round;

    private String title;

    private String description;

    private boolean free;

    private LocalDateTime createDate;

    private LocalDateTime openDate;
}

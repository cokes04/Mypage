package com.kog.mypage.novel.entity;

import com.kog.mypage.novel.entity.converter.BooleanToYNConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class Episode {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EPISODE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "NOVEL_ID")
    private Novel novel;

    private int round;                   // 회차 번호

    private String title;                // 제목

    private String description;          // 설명

    private String content;             // 내용

    @Convert(converter = BooleanToYNConverter.class)
    private boolean hidden;           // 공개 여부

    @CreatedDate
    private LocalDateTime createDate;    // 생성 일자

    private LocalDateTime openDate;    // 공개 일자

    @Builder
    public Episode(Long id, Novel novel, int round, String title, String description, String content,
                   boolean hidden, LocalDateTime createDate, LocalDateTime openDate) {
        this.id = id;
        this.novel = novel;
        this.round = round;
        this.title = title;
        this.description = description;
        this.content = content;
        this.hidden = hidden;
        this.createDate = createDate;
        this.openDate = openDate;
    }
}

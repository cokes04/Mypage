package com.kog.mypage.novel.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    private LocalDateTime createDate;    // 생성 일자

    private LocalDateTime openDate;    // 공개 일자

    @Builder
    public Episode(Long id, Novel novel, int round, String title, String description, String content,
                   LocalDateTime createDate, LocalDateTime openDate) {
        this.id = id;
        this.novel = novel;
        this.round = round;
        this.title = title;
        this.description = description;
        this.content = content;
        this.createDate = createDate;
        this.openDate = openDate;
    }

    public void changeHidden(boolean isHidden){
        if (isHidden)
            close();
        else
            open();
    }

    private void open(){
        openDate = LocalDateTime.now();
    }

    private void close(){
        openDate = null;
    }

    public boolean isHidden(){
        if (openDate == null)
            return true;
        else
            return false;
    }
}

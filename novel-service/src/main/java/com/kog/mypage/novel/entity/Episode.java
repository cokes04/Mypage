package com.kog.mypage.novel.entity;

import com.kog.mypage.novel.converter.BooleanToYNConverter;
import com.kog.mypage.novel.converter.LocalDateTimeConverter;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@ToString
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "EPISODE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "NOVEL_ID")
    private Novel novel;

    @Column(nullable = false)
    private String title;                // 제목

    private String description;          // 설명

    @Column(nullable = false)
    private String content;             // 내용

    @Column(nullable = false)
    private int orderValue;

    @Convert(converter = BooleanToYNConverter.class)
    private boolean free;           // 무료소설 여부

    @CreatedDate
    private LocalDateTime createdDate;    // 생성 일자

    @Column(nullable = true)
    private LocalDateTime openDate;    // 공개 일자

    @Builder
    public Episode(Long id, Novel novel, int orderValue, String title, String description, String content,
                   boolean free, LocalDateTime createdDate, LocalDateTime openDate) {
        this.id = id;
        this.novel = novel;
        this.orderValue = orderValue;
        this.title = title;
        this.description = description;
        this.content = content;
        this.free = free;
        this.createdDate = createdDate;
        this.openDate = openDate;
    }

    public boolean isOwner(Long userId){
        return this.getNovel().isOwner(userId) ? true : false;
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

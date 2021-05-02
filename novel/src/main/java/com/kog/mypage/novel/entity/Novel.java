package com.kog.mypage.novel.entity;

import com.kog.mypage.novel.entity.enumerate.AgeGrade;
import com.kog.mypage.novel.entity.enumerate.Genre;
import com.kog.mypage.novel.entity.enumerate.SerialCycle;
import com.kog.mypage.novel.entity.converter.SerialCycleConverter;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@ToString
@EntityListeners(AuditingEntityListener.class)
public class Novel {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "NOVEL_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name ="AUTOR_ID")
    private Author author;                      //작가

    @ManyToOne
    @JoinColumn(name = "Publisher_id")
    private Publisher publisher;                // 퍼블리셔?

    @Column(unique = true, nullable = false)
    private String title;                       // 제목

    private String description;                 // 설명

    @Column(name = "price", columnDefinition = "integer default 100", nullable = false)
    private int pricePerEpisode;                // 회차당 가격

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int freeEpisodeCount;               // 첫편부터 무료 회차 수

    @Column(nullable =  false)
    @Enumerated(EnumType.STRING)
    private AgeGrade ageGrade;                 //연령 등급

    @Column(nullable =  false)
    @Enumerated(EnumType.STRING)
    private Genre genre;                        //장르

    @Column(nullable =  false)
    @Convert(converter = SerialCycleConverter.class)
    private List<SerialCycle> serialCycle;      // 연재 주기

    @CreatedDate
    private LocalDateTime createdDate;          // 생성 일자

    private LocalDateTime startSaleDate;        // 판매 시작 일자

    @LastModifiedDate
    private LocalDateTime lastReleaseDate;      // 최근 연재 일자

    @Builder
    public Novel(Long id, Author author, Publisher publisher, String title, String description, int pricePerEpisode,
                 int freeEpisodeCount, AgeGrade ageGrade, Genre genre, List<SerialCycle> serialCycle,
                 LocalDateTime createdDate, LocalDateTime startSaleDate, LocalDateTime lastReleaseDate) {
        this.id = id;
        this.author = author;
        this.publisher = publisher;
        this.title = title;
        this.description = description;
        this.pricePerEpisode = pricePerEpisode;
        this.freeEpisodeCount = freeEpisodeCount;
        this.ageGrade = ageGrade;
        this.genre = genre;
        this.serialCycle = serialCycle;
        this.createdDate = createdDate;
        this.startSaleDate = startSaleDate;
        this.lastReleaseDate = lastReleaseDate;
    }
}

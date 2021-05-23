package com.kog.mypage.novel.entity;

import com.kog.mypage.novel.converter.BooleanToYNConverter;
import com.kog.mypage.novel.converter.GenreListToStringConverter;
import com.kog.mypage.novel.converter.SerialCycleListToStringConverter;
import com.kog.mypage.novel.enumerate.AgeGrade;
import com.kog.mypage.novel.enumerate.Genre;
import com.kog.mypage.novel.enumerate.SerialCycle;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@ToString
@Entity
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Novel {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "NOVEL_ID")
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "PUBLISHER_ID")
    private Publisher publisher;                // 퍼블리셔?

    @Column(unique = true, nullable = false, length = 100)
    private String title;                       // 제목

    @Column(columnDefinition = "varchar(1000) default ''", length = 1000)
    private String description;                 // 설명

    @OneToMany(mappedBy = "novel", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Episode> episodes;

    @Column(nullable = false)
    @Convert(converter = BooleanToYNConverter.class)
    private boolean exclusive;                //독점 소설 여부

    @Column(name = "price", columnDefinition = "integer default 100", nullable = false)
    private int pricePerEpisode;         // 회차당 가격

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int freeEpisodeCount;               // 첫편부터 무료 회차 수

    @Column(nullable =  false)
    @Enumerated(EnumType.STRING)
    private AgeGrade ageGrade;                 //연령 등급

    @Column(nullable =  false)
    @Convert(converter = GenreListToStringConverter.class)
    private List<Genre> genre;                  //장르

    @Column(nullable =  false)
    @Convert(converter = SerialCycleListToStringConverter.class)
    private List<SerialCycle> serialCycle;      // 연재 주기

    @CreatedDate
    private LocalDateTime createdDate;          // 생성 일자

    private LocalDateTime openDate;             // 공개 일자

    private LocalDateTime lastReleaseDate;      // 최근 연재 일자

    @Builder
    public Novel(Long id, Long userId, Publisher publisher, String title, String description, List<Episode> episodes,
                 boolean exclusive, int pricePerEpisode, int freeEpisodeCount, AgeGrade ageGrade, List<Genre> genre, List<SerialCycle> serialCycle,
                 LocalDateTime createdDate, LocalDateTime openDate, LocalDateTime lastReleaseDate) {
        this.id = id;
        this.userId = userId;
        this.publisher = publisher;
        this.title = title;
        this.description = description;
        this.episodes = episodes;
        this.exclusive = exclusive;
        this.pricePerEpisode = pricePerEpisode;
        this.freeEpisodeCount = freeEpisodeCount;
        this.ageGrade = ageGrade;
        this.genre = genre;
        this.serialCycle = serialCycle;
        this.createdDate = createdDate;
        this.openDate = openDate;
        this.lastReleaseDate = lastReleaseDate;
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

    public boolean isAdultNovel(){
        return this.getAgeGrade().equals(AgeGrade.NINETEENL);
    }

    public void updateReleaseDate(){
        lastReleaseDate = LocalDateTime.now();
    }

}

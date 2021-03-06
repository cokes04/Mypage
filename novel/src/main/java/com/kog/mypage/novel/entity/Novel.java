package com.kog.mypage.novel.entity;

import com.kog.mypage.novel.entity.enumerate.AgeGrade;
import com.kog.mypage.novel.entity.enumerate.Genre;
import com.kog.mypage.novel.entity.enumerate.SerialCycle;
import com.kog.mypage.novel.entity.enumerate.SerialCycleConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Novel {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "NOVEL_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name ="AUTOR_ID")
    private Author author;

    @Column(unique = true)
    private String title;

    @Column(name = "price")
    private int pricePerEpisode;

    @Enumerated(EnumType.STRING)
    private AgeGrade agetGrade;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Convert(converter = SerialCycleConverter.class)
    private List<SerialCycle> serialCycle;

    @CreatedDate
    private LocalDateTime createdDate;

    @OneToMany(mappedBy = "novel")
    private List<Episode> episode = new ArrayList<Episode>();

    @Builder
    public Novel(Long id, Author author, String title, int pricePerEpisode, AgeGrade agetGrade, Genre genre, List<SerialCycle> serialCycle, LocalDateTime createdDate, List<Episode> episode) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.pricePerEpisode = pricePerEpisode;
        this.agetGrade = agetGrade;
        this.genre = genre;
        this.serialCycle = serialCycle;
        this.createdDate = createdDate;
        this.episode = episode;
    }
}

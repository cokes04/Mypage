package com.kog.mypage.novel.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    private int round;

    private String title;

    private String contents;

    @Builder
    public Episode(Long id, Novel novel, int round, String title, String contents) {
        this.id = id;
        this.novel = novel;
        this.round = round;
        this.title = title;
        this.contents = contents;
    }
}

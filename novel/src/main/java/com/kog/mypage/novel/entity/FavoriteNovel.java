package com.kog.mypage.novel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@NoArgsConstructor
@Getter
@Entity
public class FavoriteNovel {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "NOVEL_ID")
    private Novel novel;

    public FavoriteNovel(User user, Novel novel) {
        this.user = user;
        this.novel = novel;
    }
}
package com.kog.mypage.item.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
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

    private String title;

    private String contents;
}

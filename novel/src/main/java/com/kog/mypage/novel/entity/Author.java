package com.kog.mypage.novel.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Author {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AUTHOR_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "author")
    List<Novel> novels = new ArrayList<Novel>();

    public Author(String name) {
        this.name = name;
    }
}

package com.kog.mypage.novel.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Author {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AUTHOR_ID")
    private Long id;

    @Column(nullable = false)
    private String name;

    private String email;

    private String address;

    @CreatedDate
    private LocalDateTime createDate;
    @Builder
    public Author(Long id, String name, String email, String address, LocalDateTime createDate ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.address = address;
        this.createDate = createDate;
    }
}

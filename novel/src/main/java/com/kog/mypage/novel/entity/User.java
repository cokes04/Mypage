package com.kog.mypage.novel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kog.mypage.novel.entity.enumerate.AuthProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private String providerId;

    @Builder
    public User(Long id, String name, String email, String password, AuthProvider authProvider, String providerId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.authProvider = authProvider;
        this.providerId = providerId;
    }
}

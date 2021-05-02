package com.kog.mypage.novel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kog.mypage.novel.entity.enumerate.AuthProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    @Column(nullable = false)
    private String roles;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private String providerId;



    @Builder
    public User(Long id, String name, String email, String password, AuthProvider authProvider, String providerId, String roles) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.authProvider = authProvider;
        this.providerId = providerId;
        this.roles = roles;
    }

    public List<String> getRoleList(){
        if(this.roles.length()>0){
            return Arrays.asList(this.roles.split(","));
        }
        return Collections.emptyList();
    }
}

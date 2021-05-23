package com.kog.mypage.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String roles;

    @Enumerated(EnumType.STRING)
    private AuthProvider authProvider;

    private String providerId;

    @CreatedDate
    private LocalDateTime createdDate;          // 생성 일자


    @Builder
    public User(Long id, String name, String email, String password, String roles, AuthProvider authProvider, String providerId, LocalDateTime createdDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.authProvider = authProvider;
        this.providerId = providerId;
        this.createdDate = createdDate;
    }

    public List<String> getRoleList(){
        if(this.roles.length()>0){
            return Arrays.asList(this.roles.split(","));
        }
        return Collections.emptyList();
    }
}

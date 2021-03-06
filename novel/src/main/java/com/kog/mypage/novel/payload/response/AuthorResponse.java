package com.kog.mypage.novel.payload.response;

import com.kog.mypage.novel.entity.Author;
import lombok.Builder;
import lombok.Getter;

@Getter
public class AuthorResponse {
    private String name;

    @Builder
    public AuthorResponse(String name) {
        this.name = name;
    }

    public static AuthorResponse of(Author author){
        return AuthorResponse.builder()
                .name(author.getName())
                .build();
    }
}

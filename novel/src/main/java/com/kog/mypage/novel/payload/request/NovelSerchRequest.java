package com.kog.mypage.novel.payload.request;

import lombok.Getter;;
import org.springframework.data.domain.Pageable;

@Getter
public class NovelSerchRequest extends SimplePageRequest {

    private String word;

    public Pageable getPageble(){
        return this.of();
    }
}

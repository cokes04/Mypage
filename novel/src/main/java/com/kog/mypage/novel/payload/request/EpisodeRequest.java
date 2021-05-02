package com.kog.mypage.novel.payload.request;

import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class EpisodeRequest extends SimplePageRequest {

    private Long novelId;

    public Pageable getPageble(){
        return this.of();
    }

}

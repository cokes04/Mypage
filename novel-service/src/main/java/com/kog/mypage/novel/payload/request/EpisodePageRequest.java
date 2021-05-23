package com.kog.mypage.novel.payload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EpisodePageRequest {

    private int page;

    private int size;

    private String strandard;
}

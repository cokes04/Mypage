package com.kog.mypage.novel.payload.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NovelPageRequest {

    private int page;

    private int size;

    private String standard;
}

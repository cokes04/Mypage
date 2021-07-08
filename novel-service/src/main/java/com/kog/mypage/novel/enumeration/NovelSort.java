package com.kog.mypage.novel.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
@Getter
public enum NovelSort {
    NEWEST(Sort.by(Sort.Direction.DESC, "createdDate")),
    OLDEST(Sort.by(Sort.Direction.ASC,"createdDate"));

    private final Sort sort;
}

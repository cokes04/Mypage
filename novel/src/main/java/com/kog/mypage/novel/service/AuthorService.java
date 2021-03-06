package com.kog.mypage.novel.service;

import com.kog.mypage.novel.entity.Novel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AuthorService {
    Page<Novel> getAuthorsNovel(String name, Pageable pageable);
}

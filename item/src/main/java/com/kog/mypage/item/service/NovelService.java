package com.kog.mypage.item.service;

import com.kog.mypage.item.entity.Novel;

public interface NovelService {

    Novel getNovel(String title);

    Novel addNovel(Novel novel);
}

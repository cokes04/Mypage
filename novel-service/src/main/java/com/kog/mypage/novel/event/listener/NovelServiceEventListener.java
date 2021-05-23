package com.kog.mypage.novel.event.listener;

import com.kog.mypage.novel.service.NovelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NovelServiceEventListener {
    private final NovelService novelService;
}

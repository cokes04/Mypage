package com.kog.mypage.novel.event.listener;

import com.kog.mypage.novel.event.event.DeletedNovelEvent;
import com.kog.mypage.novel.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class EpisodeServiceEventListener {

    private final EpisodeService episodeService;

}

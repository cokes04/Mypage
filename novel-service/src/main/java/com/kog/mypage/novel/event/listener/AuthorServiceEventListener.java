package com.kog.mypage.novel.event.listener;

import com.kog.mypage.novel.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthorServiceEventListener {
    private final AuthorService authorService;
}

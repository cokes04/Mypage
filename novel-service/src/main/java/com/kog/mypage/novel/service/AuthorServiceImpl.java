package com.kog.mypage.novel.service;

import com.kog.mypage.novel.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService{

    private final AuthorRepository authorRepository;
    private final ApplicationEventPublisher publisher;

}

package com.kog.mypage.novel.service;

import com.kog.mypage.novel.entity.Author;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    public final AuthorRepository authorRepository;

    @Override
    public Author joinAuthor(Author author) {
        return null;
    }
}

package com.kog.mypage.novel.service;

import com.kog.mypage.novel.entity.Author;
import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.enumerate.Genre;
import com.kog.mypage.novel.exception.OverlapTitleNovelException;
import com.kog.mypage.novel.repository.AuthorRepository;
import com.kog.mypage.novel.repository.NovelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NovelServiceImpl implements NovelService {

    private final NovelRepository novelRepository;

    @Override
    public Page<Novel> getAllNovel(Pageable pageable) {
        return novelRepository.findAll(pageable);
    }

    @Override
    public List<Novel> getAllNovel() {
        return novelRepository.findAll();
    }

    @Override
    public Optional<Novel> getNovel(Long novelId) {
        return novelRepository.findById(novelId);
    }

   @Override
    public Optional<Novel> getNovelByTitle(String title) {
        return novelRepository.findByTitle(title);
    }

    @Override
    public List<Novel> getNovelByAuthorName(String name) {
        return null;
    }

    @Override
    public Page<Novel> getNovelByGenre(Genre genre, Pageable pageable) {
        return novelRepository.findByGenre(genre, pageable);
    }

    @Override
    public Novel addNovel(Novel novel) {
        if (!novelRepository.existsByTitle(novel.getTitle())){
            return novelRepository.save(novel);
        }
        else {
            throw new OverlapTitleNovelException();
        }
    }

    @Override
    public Novel updateNovel(Novel novel) {
        if (novelRepository.existsByTitle(novel.getTitle()) && novelRepository.existsById(novel.getId())){
            return novelRepository.save(novel);
        }
        else {
            throw new RuntimeException();
        }
    }

    @Override
    public void deleteNovel(Novel novel) {
        novelRepository.delete(novel);
    }

}

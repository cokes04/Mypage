package com.kog.mypage.novel.service;

import com.kog.mypage.novel.entity.Author;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.enumerate.AgeGrade;
import com.kog.mypage.novel.entity.enumerate.Genre;
import com.kog.mypage.novel.entity.enumerate.SerialCycle;
import com.kog.mypage.novel.exception.OverlapTitleNovelException;
import com.kog.mypage.novel.repository.NovelRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

class NovelServiceImplTest {

    private NovelService novelService;

    private NovelRepository novelRepository;

    private List<Author> authors;
    private List<Novel> novels;

    @BeforeAll
    static void setup(){

    }

    @BeforeEach
    void setupEach(){
        novelRepository = Mockito.mock(NovelRepository.class);
        novelService = new NovelServiceImpl(novelRepository);

        authors = new ArrayList<Author>();
        novels = new ArrayList<Novel>();

        authors.add( new Author((long) 1,"kim", Arrays.asList()) );
        authors.add( new Author((long) 2, "lee", Arrays.asList()) );

        novels.add(
                new Novel((long) 1,
                        authors.get(0),
                        "ABC",
                        100,
                        AgeGrade.ALL,
                        Genre.FANTASY,
                        Arrays.asList(SerialCycle.SUNDAY, SerialCycle.FRIDAY),
                        null,
                        null
                )
        );
         novels.add(
                 new Novel((long)2,
                         authors.get(0),"짱구",
                         1000,
                         AgeGrade.FIFTEEN,
                         Genre.ROMANCE,
                         Arrays.asList(SerialCycle.SUNDAY, SerialCycle.WEDNESDAY, SerialCycle.SATURDAY),
                         null,
                         null
                 )
         );
        novels.add(
                new Novel((long)3,
                        authors.get(1),"Super Human",
                        10000,
                        AgeGrade.ALL,
                        Genre.MARTIAL_ARTS,
                        Arrays.asList(SerialCycle.MONDAY, SerialCycle.FRIDAY, SerialCycle.SATURDAY),
                        null,
                        null
                )
        );
        
    }

    @Test
    void saveNoOverlapTitleNovel() {
        Novel novel1 = novels.get(0);
        Novel novel2 = novels.get(1);

        when( novelRepository.existsByTitle(any(String.class)) ).thenReturn(false);
        when( novelRepository.save(novel1)).thenReturn(novel1);
        when( novelRepository.save(novel2)).thenReturn(novel2);

        Novel resultNovel1 = novelService.addNovel(novel1);
        Novel resultNovel2 = novelService.addNovel(novel2);

        assertEquals(novel1, resultNovel1);
        assertEquals(novel2, resultNovel2);

    }

    @Test
    void saveOverlapTitleNovel() {
        Novel novel = novels.get(0);
        when( novelRepository.existsByTitle(novel.getTitle()) ).thenReturn(true);

        Throwable exception = assertThrows(OverlapTitleNovelException.class, () -> {novelService.addNovel(novel); } );
    }

    @Test
    void updateNovel() {

    }
}
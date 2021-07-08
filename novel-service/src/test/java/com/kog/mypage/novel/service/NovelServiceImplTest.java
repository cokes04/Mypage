package com.kog.mypage.novel.service;

import com.kog.mypage.novel.dto.UserInfoDto;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.enumeration.AgeGrade;
import com.kog.mypage.novel.enumeration.Genre;
import com.kog.mypage.novel.enumeration.SerialCycle;
import com.kog.mypage.novel.exception.NonExistentEntityException;
import com.kog.mypage.novel.repository.NovelRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationEventPublisher;


import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

class NovelServiceImplTest {

    private NovelService novelService;

    private NovelRepository novelRepository;

    private UserInfoDto authorOfNovel1and2; // novel 1,2 저자 겸 독자

    private UserInfoDto authorOfNovel3; // novel 2, 3 저자 겸 독자

    private UserInfoDto user;       // 그냥 독자

    private UserInfoDto admin;      // 관리자

    Novel novel1;
    Novel novel2Close;
    Novel novel3Adult;

    @BeforeAll
    static void setup(){

    }

    @BeforeEach
    void setupEach(){
        ApplicationEventPublisher eventPublisher = new ApplicationEventPublisher() {
            @Override
            public void publishEvent(Object o) {

            }
        };

        novelRepository = Mockito.mock(NovelRepository.class);
        novelService = new NovelServiceImpl(novelRepository, eventPublisher);

        authorOfNovel1and2 = UserInfoDto.builder().userId(1l).roles(Arrays.asList("USER")).build();
        authorOfNovel3 = UserInfoDto.builder().userId(2l).roles(Arrays.asList("USER")).build();
        user = UserInfoDto.builder().userId(3l).roles(Arrays.asList("USER")).build();
        admin = UserInfoDto.builder().userId(4l).roles(Arrays.asList("USER","ADMIN")).build();


        novel1 = Novel.builder()
                .id(1l)
                .userId(1l)
                .title("ABC")
                .description("haha")
                .exclusive(false)
                .pricePerEpisode(100)
                .ageGrade(AgeGrade.ALL)
                .genre(Arrays.asList(Genre.ROMANCE))
                .serialCycle(Arrays.asList(SerialCycle.SUNDAY, SerialCycle.FRIDAY))
                .openDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .lastReleaseDate(LocalDateTime.now())
                .build();

        novel2Close = Novel.builder()
                .id(2l)
                .userId(1l)
                .title("짱구")
                .description("짜아아아앙구")
                .exclusive(false)
                .pricePerEpisode(100)
                .ageGrade(AgeGrade.FIFTEEN)
                .genre(Arrays.asList(Genre.FANTASY))
                .serialCycle(Arrays.asList(SerialCycle.SUNDAY, SerialCycle.WEDNESDAY, SerialCycle.SATURDAY))
                .openDate(null)
                .createdDate(LocalDateTime.now())
                .lastReleaseDate(LocalDateTime.now())
                .build();

        novel3Adult = Novel.builder()
                        .id(3l)
                        .userId(2l)
                        .title("Super Human")
                        .description("a")
                        .exclusive(false)
                        .pricePerEpisode(100)
                        .ageGrade(AgeGrade.NINETEENL)
                        .genre(Arrays.asList(Genre.MARTIAL_ARTS))
                        .serialCycle(Arrays.asList(SerialCycle.MONDAY, SerialCycle.FRIDAY, SerialCycle.SATURDAY))
                        .openDate(LocalDateTime.now())
                        .createdDate(LocalDateTime.now())
                        .lastReleaseDate(LocalDateTime.now())
                        .build();


    }

/*    @Test
    void getOpenNovel() {
        Novel openNovel = novel1;
        when(novelRepository.findById(openNovel.getId())).thenReturn(Optional.of(novel1));

        Novel getNovel1 = novelService.getNovel(openNovel.getId(), user);
        assertEquals(openNovel, getNovel1);

        Novel getNovel2 = novelService.getNovel(openNovel.getId(), authorOfNovel1and2);
        assertEquals(openNovel, getNovel2);

        Novel getNovel3 = novelService.getNovel(openNovel.getId(), admin);
        assertEquals(openNovel, getNovel3);

    }

    @Test
    void getCloseNovel() {
        Novel closeNovel = novel2Close;
        when(novelRepository.findById(novel2Close.getId())).thenReturn(Optional.of(novel2Close));

        Novel getNovel1 = novelService.getNovel(closeNovel.getId(), admin);
        assertEquals(novel2Close, closeNovel);

        Novel getNovel2 = novelService.getNovel(closeNovel.getId(), authorOfNovel1and2);
        assertEquals(novel2Close, closeNovel);


        Throwable exception1 = assertThrows(InaccessibleEntityException.class, () -> novelService.getNovel(closeNovel.getId(), user) );
        Throwable exception2 = assertThrows(InaccessibleEntityException.class, () -> novelService.getNovel(closeNovel.getId(), authorOfNovel3) );


    }*/

    @Test
    void getNotExistingNovel() {
        Long notExistionNovelId = 11111l;
        when(novelRepository.findById(notExistionNovelId)).thenReturn(Optional.empty());

        Throwable exception = assertThrows(NonExistentEntityException.class, () -> novelService.getNovel(notExistionNovelId) );
    }

    @Test
    void getExistingNovel() {
        Long existionNovelId = novel1.getId();
        when(novelRepository.findById(existionNovelId)).thenReturn(Optional.of(novel1));

        Novel resultNovel = novelService.getNovel(existionNovelId);
        Novel expectedNovel = novel1;
        assertEquals(expectedNovel, resultNovel);
    }
    /*
    @Test
    void createNovel() {
        CreateNovelDto createNovelDto = CreateNovelDto.builder()
                .title("abc")
                .description("q")
                .ageGrade(AgeGrade.NINETEENL)
                .genre(Arrays.asList(Genre.MARTIAL_ARTS))
                .hidden(false)
                .serialCycle(Arrays.asList(SerialCycle.WEDNESDAY,SerialCycle.SUNDAY))
                .build();

        Novel expectedNovel = Novel.builder()
                .userId(user.getUserId())
                .title(createNovelDto.getTitle())
                .description(createNovelDto.getDescription())
                .ageGrade(createNovelDto.getAgeGrade())
                .genre(createNovelDto.getGenre())
                .serialCycle(createNovelDto.getSerialCycle())
                .openDate(createNovelDto.isHidden() ? null : LocalDateTime.now())
                .build();

        when( novelRepository.save(any(Novel.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        Novel resultNovel = novelService.createNovel(createNovelDto, user);

        checkSameNovel(expectedNovel, resultNovel);

    }


    @Test
    void createNovelOfOverlapTitle() {
        CreateNovelDto createNovelDto = CreateNovelDto.builder()
                .title("aaaaaaaaaaaa")
                .description("q")
                .ageGrade(AgeGrade.NINETEENL)
                .genre(Arrays.asList(Genre.MARTIAL_ARTS))
                .hidden(false)
                .serialCycle(Arrays.asList(SerialCycle.WEDNESDAY,SerialCycle.SUNDAY))
                .build();

        when( novelRepository.save(any(Novel.class)) ).thenThrow( RuntimeException.class );

        Throwable exception = assertThrows(OverlapTitleException.class, () -> novelService.createNovel(createNovelDto, user) );
    }

    @Test
    void updateNovel() {
        Novel existingNovel = novel1;

        UpdateNovelDto updateNovelDto1 = UpdateNovelDto.builder()
                .novelId(novel1.getId())
                .title(Optional.of("hahahahaha"))
                .description(Optional.of("zzzzzzzzzzzzzzzzz"))
                .hidden(Optional.of(true))
                .serialCycle(Optional.of(Arrays.asList(SerialCycle.SUNDAY)))
                .ageGrade(Optional.of(AgeGrade.NINETEENL))
                .genre(Optional.of(Arrays.asList(Genre.MARTIAL_ARTS)))
                .build();

        Novel expectedNovel1 = Novel.builder()
                .id(novel1.getId())
                .userId(novel1.getUserId())
                .title(updateNovelDto1.getTitle().get())
                .description(updateNovelDto1.getDescription().get())
                .genre(updateNovelDto1.getGenre().get())
                .serialCycle(updateNovelDto1.getSerialCycle().get())
                .ageGrade(updateNovelDto1.getAgeGrade().get())
                .pricePerEpisode(novel1.getPricePerEpisode())
                .exclusive(novel1.isExclusive())
                .publisher(novel1.getPublisher())
                .episodes(novel1.getEpisodes())
                .createdDate(novel1.getCreatedDate())
                .openDate(null)
                .lastReleaseDate(novel1.getLastReleaseDate())
                .build();


        UpdateNovelDto updateNovelDto2 = UpdateNovelDto.builder()
                .novelId(novel1.getId())
                .title(Optional.empty())
                .description(Optional.empty())
                .genre(Optional.empty())
                .ageGrade(Optional.empty())
                .serialCycle(Optional.empty())
                .hidden(Optional.empty())
                .build();

        Novel expectedNovel2 = existingNovel;

        when(novelRepository.findById(existingNovel.getId())).thenReturn(Optional.of(existingNovel));
        when(novelRepository.save(any(Novel.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());

        Novel actualNovel1 = novelService.updateNovel(updateNovelDto1, authorOfNovel1and2);
        checkSameNovel(expectedNovel1, actualNovel1);

        Novel actualNovel2 = novelService.updateNovel(updateNovelDto2, admin);
        checkSameNovel(expectedNovel2, actualNovel2);


        Throwable exception1 = assertThrows(InaccessibleEntityException.class, ()-> novelService.updateNovel(updateNovelDto1, authorOfNovel3));
        Throwable exception2 = assertThrows(InaccessibleEntityException.class, ()-> novelService.updateNovel(updateNovelDto1, user));
    }

    @Test
    void updateNotExistingNovel() {
        Long notExistingNovelId = 123456789l;

        when(novelRepository.findById(notExistingNovelId)).thenReturn(Optional.empty());

        UpdateNovelDto updateNovelDto = UpdateNovelDto.builder()
                .novelId(notExistingNovelId)
                .title(Optional.of("aaaaaaaa"))
                .description(Optional.empty())
                .genre(Optional.empty())
                .ageGrade(Optional.empty())
                .serialCycle(Optional.empty())
                .hidden(Optional.of(true))
                .build();

        Throwable exception = assertThrows(NotFoundEntityException.class, () -> novelService.updateNovel(updateNovelDto, user));
    }

    private void checkSameNovel(Novel expectedNovel, Novel actualNovel){    // createDate 검사 x
        assertEquals(expectedNovel.getId(),actualNovel.getId());
        assertEquals(expectedNovel.getUserId(),actualNovel.getUserId());
        assertEquals(expectedNovel.getTitle(), actualNovel.getTitle());
        assertEquals(expectedNovel.getDescription(), actualNovel.getDescription());
        assertEquals(expectedNovel.getAgeGrade(), actualNovel.getAgeGrade());
        assertEquals(expectedNovel.getGenre(), actualNovel.getGenre());
        assertEquals(expectedNovel.getSerialCycle(),actualNovel.getSerialCycle());
        assertEquals(expectedNovel.getPricePerEpisode(),actualNovel.getPricePerEpisode());
        assertEquals(expectedNovel.getPublisher(),actualNovel.getPublisher());
        assertEquals(expectedNovel.getEpisodes(),actualNovel.getEpisodes());

        LocalDateTime expectedNovelOpenDate = expectedNovel.getOpenDate();
        LocalDateTime actualNovelOpenDate = actualNovel.getOpenDate();

        if (actualNovelOpenDate == null)
            assertEquals(expectedNovelOpenDate, actualNovelOpenDate);
        else
            assertTrue(expectedNovelOpenDate.minusSeconds(3).isBefore(actualNovelOpenDate)
                    && expectedNovelOpenDate.plusSeconds(3).isAfter(actualNovelOpenDate));
        // 실제 opendate가 예상 opendate +-3초 사이


        LocalDateTime expectedNovelLastReleaseDate = expectedNovel.getLastReleaseDate();
        LocalDateTime actualNovelLastReleaseDate = actualNovel.getLastReleaseDate();

        if (actualNovelLastReleaseDate == null)
            assertEquals(expectedNovelLastReleaseDate, actualNovelLastReleaseDate);
        else
            assertTrue(expectedNovelLastReleaseDate.minusSeconds(3).isBefore(actualNovelLastReleaseDate)
                && expectedNovelLastReleaseDate.plusSeconds(3).isAfter(actualNovelLastReleaseDate));


    }*/
}
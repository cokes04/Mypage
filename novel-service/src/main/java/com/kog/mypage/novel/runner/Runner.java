package com.kog.mypage.novel.runner;

import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.enumerate.AgeGrade;
import com.kog.mypage.novel.enumerate.Genre;
import com.kog.mypage.novel.enumerate.SerialCycle;
import com.kog.mypage.novel.service.EpisodeService;
import com.kog.mypage.novel.service.NovelService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class Runner implements ApplicationRunner {

    private final EpisodeService episodeService;
    private final NovelService novelService;

    @Override
    public void run(ApplicationArguments args) throws Exception {


        Novel novel1 = Novel.builder()
                .userId(1l)
                .title("짱구")
                .description("재미있다.")
                .exclusive(true)
                .freeEpisodeCount(3)
                .ageGrade(AgeGrade.NINETEENL)
                .genre(Arrays.asList(Genre.FANTASY, Genre.ROMANCE))
                .serialCycle(Arrays.asList(SerialCycle.WEDNESDAY, SerialCycle.SUNDAY))
                .openDate(LocalDateTime.now())
                .build();

        novelService.createNovel(novel1);

        for (int round = 1; round <= 100; round++) {
            Episode episode = Episode.builder()
                    .novel(novel1)
                    .round(round)
                    .title(round + "화")
                    .description(round + "abcdefg")
                    .openDate(LocalDateTime.now())
                    .build();

            if (round >= 95)
                episode.changeHidden(false);

            episodeService.createEpisode(episode);
        }
    }
}

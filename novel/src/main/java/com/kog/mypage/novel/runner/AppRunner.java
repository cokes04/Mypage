package com.kog.mypage.novel.runner;


import com.kog.mypage.novel.entity.Author;
import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.enumerate.AgeGrade;
import com.kog.mypage.novel.entity.enumerate.Genre;
import com.kog.mypage.novel.entity.enumerate.SerialCycle;
import com.kog.mypage.novel.entity.enumerate.TicketType;
import com.kog.mypage.novel.entity.ticket.AddTicketRecord;
import com.kog.mypage.novel.entity.ticket.Ticket;
import com.kog.mypage.novel.entity.ticket.UseTicketRecord;
import com.kog.mypage.novel.repository.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Array;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Component
public class AppRunner implements ApplicationRunner {

    private final UserRepository userRepository;
    private final NovelRepository novelRepository;
    private final AuthorRepository authorRepository;
    private final EpisodeRepository episodeRepository;
    private final TicketRepository ticketRepository;
    private final TicketRecordRepository ticketRecordRepository;
    private final PasswordEncoder passwordEncoder;

    //테스트용
    @Override
    public void run(ApplicationArguments args) throws Exception {
        for (int i = 1; i <= 10; i++) {
            Author author = Author.builder().name(String.valueOf(i)).build();

            authorRepository.save(author);
            for (int j = 1; j <= 5; j++) {
                Novel novel = Novel.builder()
                        .author(author)
                        .title(String.valueOf(i*10000 +j))
                        .description("그냥")
                        .ageGrade(AgeGrade.FIFTEEN)
                        .genre(Genre.FANTASY)
                        .serialCycle(Arrays.asList(SerialCycle.WEDNESDAY,SerialCycle.SUNDAY))
                        .build();

                novelRepository.save(novel);
                for (int z = 1; z <= 10; z++) {
                    Episode episode = Episode.builder().novel(novel).round(z)
                            .title(z + "화").content("aaa" + z).build();

                    episodeRepository.save(episode);
                }
            }
        }
        User user = User.builder().name("kim").email("abc@haha.com").password(passwordEncoder.encode("aaaa")).roles("USER").build();
        userRepository.save(user);

        Novel novel1 = novelRepository.findById((long)2).orElseThrow();
        List<Episode> episodes1 = episodeRepository.findByNovel(novel1);
        Ticket ticket1 = Ticket.builder().user(user).novel(novel1).rentalCount(3).possessionCount(5).build();
        ticketRepository.save(ticket1);

        Novel novel2 =novelRepository.findById((long)13).orElseThrow();
        List<Episode> episodes2 = episodeRepository.findByNovel(novel2);
        Ticket ticket2 = Ticket.builder().user(user).novel(novel2).rentalCount(0).possessionCount(0).build();
        ticketRepository.save(ticket2);

        List<AddTicketRecord> addTicketRecords = new ArrayList<>();
        addTicketRecords.add( AddTicketRecord.builder().user(user).novel(novel1).count(2).ticketType(TicketType.POSSESSION).build());
        addTicketRecords.add( AddTicketRecord.builder().user(user).novel(novel1).count(5).ticketType(TicketType.POSSESSION).build());
        addTicketRecords.add( AddTicketRecord.builder().user(user).novel(novel1).count(4).ticketType(TicketType.RENTAL).build());
        addTicketRecords.add( AddTicketRecord.builder().user(user).novel(novel2).count(1).ticketType(TicketType.POSSESSION).build());
        ticketRecordRepository.saveAll(addTicketRecords);

        List<UseTicketRecord> useTicketRecords = new ArrayList<>();
        useTicketRecords.add( UseTicketRecord.builder().user(user).novel(novel1).episode(episodes1.get(0)).ticketType(TicketType.POSSESSION).build());
        useTicketRecords.add( UseTicketRecord.builder().user(user).novel(novel1).episode(episodes1.get(1)).ticketType(TicketType.POSSESSION).build());
        useTicketRecords.add( UseTicketRecord.builder().user(user).novel(novel1).episode(episodes1.get(2)).ticketType(TicketType.RENTAL).build());
        useTicketRecords.add( UseTicketRecord.builder().user(user).novel(novel2).episode(episodes2.get(1)).ticketType(TicketType.POSSESSION).build());
        ticketRecordRepository.saveAll(useTicketRecords);

    }


}

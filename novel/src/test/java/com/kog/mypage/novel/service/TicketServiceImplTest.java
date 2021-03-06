package com.kog.mypage.novel.service;

import com.kog.mypage.novel.dto.AddTicketDto;
import com.kog.mypage.novel.dto.UseTicketDto;
import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.enumerate.TicketType;
import com.kog.mypage.novel.entity.ticket.Ticket;
import com.kog.mypage.novel.entity.ticket.TicketRecord;
import com.kog.mypage.novel.entity.ticket.UseTicketRecord;
import com.kog.mypage.novel.repository.TicketRecordRepository;
import com.kog.mypage.novel.repository.TicketRepository;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;


import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TicketServiceImplTest {

    private TicketServiceImpl ticketService;
    private TicketRecordRepository ticketRecordRepository;
    private TicketRepository ticketRepository;



    private final User user = User.builder().id((long)0).build();
    private final Novel novel = Novel.builder().id((long)0).build();;

    private final Episode ep1 = Episode.builder().id( (long) 1).novel(novel).round(1).build();
    private final Episode ep2 = Episode.builder().id( (long) 2).novel(novel).round(2).build();

    private final AddTicketDto addPossessionTicketDto = AddTicketDto.builder()
            .user(user).novel(novel).count(3).ticketType(TicketType.POSSESSION).build();
    private final AddTicketDto addRentalTicketDto = AddTicketDto.builder()
            .user(user).novel(novel).count(2).ticketType(TicketType.RENTAL).build();

    private final UseTicketDto usePossessionEp1TicketDto = UseTicketDto.builder()
            .user(user).novel(novel).episode(ep1).ticketType(TicketType.POSSESSION).build();
    private final UseTicketDto useRentalEp1TicketDto = UseTicketDto.builder()
            .user(user).novel(novel).episode(ep1).ticketType(TicketType.RENTAL).build();

    private final UseTicketDto usePossessionEp2TicketDto = UseTicketDto.builder()
            .user(user).novel(novel).episode(ep2).ticketType(TicketType.POSSESSION).build();
    private final UseTicketDto useRentalEp2TicketDto = UseTicketDto.builder()
            .user(user).novel(novel).episode(ep2).ticketType(TicketType.RENTAL).build();

    @BeforeEach
    void setupEach() {
        ticketRecordRepository = Mockito.mock(TicketRecordRepository.class);
        ticketRepository = Mockito.mock(TicketRepository.class);
        ticketService = new TicketServiceImpl(ticketRepository, ticketRecordRepository);
    }

    @Test
    void successfirstBuyTicket() {
        when(ticketRepository.findByUserAndNovel(any(User.class), any(Novel.class)))
                .thenReturn(Optional.empty());
        when(ticketRepository.save(any(Ticket.class)))
                .thenAnswer( invocation ->  invocation.getArgument(0) );

        Ticket actualTicket1 = ticketService.addTicket(addPossessionTicketDto);
        verify(ticketRecordRepository,times(1)).save(any(TicketRecord.class));

        cheackTicket(actualTicket1, user, novel, 3, 0);
    }

    @Test
    void failureFirstUseTicket() {
        when(ticketRepository.findByUserAndNovel(any(User.class), any(Novel.class)))
                .thenReturn(Optional.empty());
        when(ticketRepository.save(any(Ticket.class)))
                .thenAnswer( invocation ->  invocation.getArgument(0) );

        RuntimeException exception = assertThrows(RuntimeException.class, () -> ticketService.useTicket(usePossessionEp1TicketDto));
        String massage = exception.getMessage();
        System.out.println(massage);
    }


    @Test
    void successChangeTicket() {
        //기존의 티켓
        Ticket existingTicket = Ticket.builder().id((long) 0).user(user).novel(novel).possessionCount(3).rentalCount(3).build();

        when(ticketRepository.findByUserAndNovel(any(User.class), any(Novel.class)))
                .thenReturn(Optional.of(existingTicket));

        //소장 티켓 추가
        Ticket actualTicket1 = ticketService.addTicket(addPossessionTicketDto);
        cheackTicket(actualTicket1, user, novel, 6, 3);
        //렌탈 티켓 추가
        Ticket actualTicket2 = ticketService.addTicket(addRentalTicketDto);
        cheackTicket(actualTicket2, user, novel, 6, 5);
        //에피소드 1 소장
        Ticket actualTicket3 = ticketService.useTicket(usePossessionEp1TicketDto);
        cheackTicket(actualTicket3, user, novel, 5, 5);
        //에피소드 3 대여
        Ticket actualTicket4 = ticketService.useTicket(useRentalEp2TicketDto);
        cheackTicket(actualTicket4, user, novel, 5, 4);

        verify(ticketRepository, times(4)).findByUserAndNovel(user, novel);
        verify(ticketRepository, times(0)).save(any(Ticket.class));
        verify(ticketRecordRepository,times(4)).save(any(TicketRecord.class));
    }


    @Test
    void InsufficientCountOftickets() { // 티켓 갯수 부족 (해당 티켓 0개 보유)
        //기존의 티켓
        Ticket existingTicket = Ticket.builder().id((long) 0).user(user).novel(novel).possessionCount(0).rentalCount(0).build();

        when(ticketRepository.findByUserAndNovel(any(User.class), any(Novel.class)))
                .thenReturn(Optional.of(existingTicket));
        when(ticketRecordRepository.findByUserAndEpisode(user, ep1))
                .thenReturn(Collections.emptyList());

        //에피소드 1 소유
        RuntimeException exception1 = assertThrows( RuntimeException.class, () -> ticketService.useTicket(usePossessionEp1TicketDto));
        String massage1 = exception1.getMessage();
        System.out.println(massage1);

        //에피소드 1 대여
        RuntimeException exception2 = assertThrows( RuntimeException.class, () -> ticketService.useTicket(useRentalEp1TicketDto));
        String massage2 = exception2.getMessage();
        System.out.println(massage2);
    }


    @Test
    void useTicketForExpiredRentalPeriodEpisode() { // 대여기간 만료된 에피소드 구매

        ArrayList<UseTicketRecord> useTicketEp1Records = new ArrayList<UseTicketRecord>();
        useTicketEp1Records.add( // ep1의 RENTAL_HOUR + 1 시간 전 대여 기록 (대여 끝나는 시간에서 1시간 지남)
                UseTicketRecord.builder().ticketType(TicketType.RENTAL).createdDate(LocalDateTime.now().minusHours(ticketService.RENTAL_HOUR + 1)).build());

        //기존의 티켓
        Ticket existingTicket = Ticket.builder().id((long) 0).user(user).novel(novel).possessionCount(1).rentalCount(2).build();

        when(ticketRepository.findByUserAndNovel(any(User.class), any(Novel.class)))
                .thenReturn(Optional.of(existingTicket));
        when(ticketRecordRepository.findByUserAndEpisode(user, ep1))
                .thenReturn(useTicketEp1Records);

        //에피소드 1 소유
        Ticket ticket = ticketService.useTicket(usePossessionEp1TicketDto);
        cheackTicket(ticket, user, novel, 0, 2);
    }

    @Test
    void useTicketForPurchasedEpisode() { // 대여중인 에피소드, 소유중인 에피소드 구매
        ArrayList<UseTicketRecord> useTicketEp1Records = new ArrayList<UseTicketRecord>();
        useTicketEp1Records.add( // ep1의 RENTAL_HOUR - 1 시간 전 대여 기록 (대여기간까지 1시간 남음)
                UseTicketRecord.builder().ticketType(TicketType.RENTAL).createdDate(LocalDateTime.now().minusHours(ticketService.RENTAL_HOUR - 1)).build());

        ArrayList<UseTicketRecord> useTicketEp2Records = new ArrayList<UseTicketRecord>();
        useTicketEp2Records.add( // ep2의 333년 전 소유 기록
                UseTicketRecord.builder().ticketType(TicketType.POSSESSION).createdDate(LocalDateTime.now().minusYears(333)).build());


        UseTicketDto useTicketDto1 = UseTicketDto.builder()
                .user(user).novel(novel).episode(ep1).ticketType(TicketType.POSSESSION).build();
        UseTicketDto useTicketDto2 = UseTicketDto.builder()
                .user(user).novel(novel).episode(ep2).ticketType(TicketType.RENTAL).build();


        when(ticketRecordRepository.findByUserAndEpisode(user, ep1))
                .thenReturn(useTicketEp1Records);
        when(ticketRecordRepository.findByUserAndEpisode(user, ep2))
                .thenReturn(useTicketEp2Records);

        //에피소드 1 대여
        RuntimeException exception1 = assertThrows(RuntimeException.class, () -> ticketService.useTicket(useTicketDto1)) ;
        String massage1 = exception1.getMessage();
        System.out.println(massage1);

        //에피소드 2 소유
        RuntimeException exception2 = assertThrows(RuntimeException.class, () -> ticketService.useTicket(useTicketDto2)) ;
        String massage2 = exception2.getMessage();
        System.out.println(massage2);

    }

    private void cheackTicket(Ticket actualTicket, User expectedUser, Novel expectedNovel, int expectedPossessionCount, int expectedRentalCount ){
        assertNotNull(actualTicket);
        assertEquals(expectedUser, actualTicket.getUser());
        assertEquals(expectedNovel, actualTicket.getNovel());
        assertEquals(expectedPossessionCount, actualTicket.getPossessionCount());
        assertEquals(expectedRentalCount, actualTicket.getRentalCount());
    }

}
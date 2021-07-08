package com.kog.mypage.ticket.service;

import com.kog.mypage.ticket.dto.AddTicketDto;
import com.kog.mypage.ticket.dto.UseTicketDto;
import com.kog.mypage.ticket.dto.UserInfoDto;
import com.kog.mypage.ticket.enumeration.TicketType;


import com.kog.mypage.ticket.repository.TicketRepository;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;

class TicketServiceImplTest {

    private TicketServiceImpl ticketService;
    private TicketRecordService ticketRecordService;
    private TicketRepository ticketRepository;

    private final UserInfoDto user = UserInfoDto.builder().userId(123l).roles(Arrays.asList("USER")).build();

    private final Long novelId = 1l;
    private final Long ep1Id = 1l;
    private final Long ep2Id = 2l;

    private final AddTicketDto addPossessionTicketDto = AddTicketDto.builder()
            .novelId(novelId).count(3).ticketType(TicketType.POSSESSION).build();
    private final AddTicketDto addRentalTicketDto = AddTicketDto.builder()
            .novelId(novelId).count(2).ticketType(TicketType.RENTAL).build();

    private final UseTicketDto usePossessionEp1TicketDto = UseTicketDto.builder()
            .novelId(novelId).episodeId(ep1Id).ticketType(TicketType.POSSESSION).build();
    private final UseTicketDto useRentalEp1TicketDto = UseTicketDto.builder()
            .novelId(novelId).episodeId(ep1Id).ticketType(TicketType.RENTAL).build();

    private final UseTicketDto usePossessionEp2TicketDto = UseTicketDto.builder()
            .novelId(novelId).episodeId(ep2Id).ticketType(TicketType.POSSESSION).build();
    private final UseTicketDto useRentalEp2TicketDto = UseTicketDto.builder()
            .novelId(novelId).episodeId(ep2Id).ticketType(TicketType.RENTAL).build();

    /*@BeforeEach
    void setupEach() {
        ticketRepository = Mockito.mock(TicketRepository.class);
        ticketRecordService = Mockito.mock(TicketRecordService.class);
        ticketService = new TicketServiceImpl(ticketRepository, ticketRecordService);
    }

    @Test
    void successfirstAddTicket() {
        when(ticketRecordService.createAddTicketRecord(any(AddTicketDto.class), any(UserInfoDto.class)))
                .thenReturn(null);
        when(ticketRepository.findByNovelIdAndUserId(any(Long.class), any(Long.class)))
                .thenReturn(Optional.empty());
        when(ticketRepository.save(any(Ticket.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        Ticket actualTicket = ticketService.addTicket(addPossessionTicketDto, user);

        cheackTicket(actualTicket, user.getUserId(), novelId, addPossessionTicketDto.getCount(), 0);

        verify(ticketRecordService,times(1)).createAddTicketRecord(addPossessionTicketDto, user);
        verify(ticketRepository,times(1)).findByNovelIdAndUserId(novelId, user.getUserId());
        verify(ticketRepository,times(1)).save(actualTicket);
    }

    @Test
    void successChangeTicket() {
        //기존의 티켓
        Ticket existingTicket = Ticket.builder().id((long) 0).userId(user.getUserId()).novelId(novelId).possessionCount(3).rentalCount(3).build();

        when(ticketRecordService.createAddTicketRecord(any(AddTicketDto.class), any(UserInfoDto.class)))
                .thenReturn(null);
        when(ticketRecordService.createUseTicketRecord(any(UseTicketDto.class), any(UserInfoDto.class)))
                .thenReturn(null);
        when(ticketRepository.findByNovelIdAndUserId(any(Long.class), any(Long.class)))
                .thenReturn(Optional.of(existingTicket));

        when(ticketRepository.save(any(Ticket.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        //소장 티켓 추가
        Ticket actualTicket1 = ticketService.addTicket(addPossessionTicketDto, user);
        cheackTicket(actualTicket1, user.getUserId(), novelId, 6, 3);
        //렌탈 티켓 추가
        Ticket actualTicket2 = ticketService.addTicket(addRentalTicketDto, user);
        cheackTicket(actualTicket2, user.getUserId(), novelId, 6, 5);
        //에피소드 1 소장
        Ticket actualTicket3 = ticketService.useTicket(usePossessionEp1TicketDto, user);
        cheackTicket(actualTicket3, user.getUserId(), novelId, 5, 5);
        //에피소드 3 대여
        Ticket actualTicket4 = ticketService.useTicket(useRentalEp2TicketDto, user);
        cheackTicket(actualTicket4, user.getUserId(), novelId, 5, 4);

        verify(ticketRepository, times(4)).findByNovelIdAndUserId(novelId, user.getUserId());
        verify(ticketRepository, times(4)).save(any(Ticket.class));

        verify(ticketRecordService,times(1)).createAddTicketRecord(addPossessionTicketDto, user);
        verify(ticketRecordService,times(1)).createAddTicketRecord(addRentalTicketDto, user);
        verify(ticketRecordService,times(1)).createUseTicketRecord(usePossessionEp1TicketDto, user);
        verify(ticketRecordService,times(1)).createUseTicketRecord(useRentalEp2TicketDto, user);
    }


    @Test
    void InsufficientCountOftickets() { // 티켓 갯수 부족
        when(ticketRepository.findByNovelIdAndUserId(any(Long.class), any(Long.class)))
                .thenReturn(Optional.empty());
        when(ticketRecordService.createUseTicketRecord(any(UseTicketDto.class), any(UserInfoDto.class)))
                .thenReturn(null);
        when(ticketRecordService.isPurchasedEpisode(ep1Id, user))
                .thenReturn(false);

        //에피소드 1 소유
        RuntimeException exception1 = assertThrows( LackTicketException.class, () -> ticketService.useTicket(usePossessionEp1TicketDto, user));
        System.out.println(exception1.getMessage());

        //에피소드 1 대여
        RuntimeException exception2 = assertThrows( LackTicketException.class, () -> ticketService.useTicket(useRentalEp1TicketDto, user));
        System.out.println(exception2.getMessage());

        verify(ticketRepository, times(2)).findByNovelIdAndUserId(any(Long.class),any(Long.class));
        verify(ticketRepository, times(0)).save(any(Ticket.class));
        verify(ticketRecordService, times(0)).createUseTicketRecord(any(UseTicketDto.class),any(UserInfoDto.class));

    }


    @Test
    void useTicketForExpiredRentalPeriodEpisode() { // 미구매 또는 대여기간 만료 에피소드 구매
        Ticket existingTicket = Ticket.builder().id((long) 0).novelId(novelId).userId(user.getUserId()).possessionCount(12345).rentalCount(5232).build();
        int existingPossessionCount = existingTicket.getPossessionCount();
        int existingRentalCount = existingTicket.getRentalCount();

        when(ticketRepository.findByNovelIdAndUserId(any(Long.class), any(Long.class)))
                .thenReturn(Optional.of(existingTicket));
        when(ticketRecordService.createUseTicketRecord(any(UseTicketDto.class), any(UserInfoDto.class)))
                .thenReturn(null);
        when(ticketRecordService.isPurchasedEpisode(any(Long.class), any(UserInfoDto.class)))
                .thenReturn(false);
        when(ticketRepository.save(any(Ticket.class)))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        Ticket actualTicket1 = ticketService.useTicket(usePossessionEp1TicketDto, user);
        cheackTicket(actualTicket1, user.getUserId(), novelId, --existingPossessionCount, existingRentalCount);

        Ticket actualTicket2 = ticketService.useTicket(useRentalEp2TicketDto, user);
        cheackTicket(actualTicket2, user.getUserId(), novelId, existingPossessionCount, --existingRentalCount);

        verify(ticketRepository, times(2)).findByNovelIdAndUserId(any(Long.class),any(Long.class));
        verify(ticketRecordService, times(2)).isPurchasedEpisode(any(Long.class),any(UserInfoDto.class));
        verify(ticketRecordService, times(2)).createUseTicketRecord(any(UseTicketDto.class),any(UserInfoDto.class));
    }

    @Test
    void useTicketForPurchasedEpisode() { // 대여중인 에피소드, 소유중인 에피소드 구매
        Ticket existingTicket = Ticket.builder().novelId(novelId).userId(user.getUserId()).rentalCount(11111).possessionCount(22222).build();
        int existingPossessionCount = existingTicket.getPossessionCount();
        int existingRentalCount = existingTicket.getRentalCount();

        when(ticketRepository.findByNovelIdAndUserId(any(Long.class), any(Long.class)))
                .thenReturn(Optional.of(existingTicket));
        when(ticketRecordService.isPurchasedEpisode(any(Long.class), any(UserInfoDto.class)))
                .thenReturn(true);

        Ticket actualTicket1 = ticketService.useTicket(useRentalEp1TicketDto, user);
        cheackTicket(actualTicket1, user.getUserId(), novelId, existingPossessionCount, existingRentalCount);

        Ticket actualTicket2 = ticketService.useTicket(usePossessionEp2TicketDto, user);
        cheackTicket(actualTicket2, user.getUserId(), novelId, existingPossessionCount, existingRentalCount);


        verify(ticketRepository, times(2)).findByNovelIdAndUserId(any(Long.class),any(Long.class));
        verify(ticketRecordService, times(2)).isPurchasedEpisode(any(Long.class),any(UserInfoDto.class));
        verify(ticketRecordService, times(0)).createUseTicketRecord(any(UseTicketDto.class),any(UserInfoDto.class));

    }

    private void cheackTicket(Ticket actualTicket, Long expectedUserId, Long expectedNovelId, int expectedPossessionCount, int expectedRentalCount ){
        assertNotNull(actualTicket);
        assertEquals(expectedUserId, actualTicket.getUserId());
        assertEquals(expectedNovelId, actualTicket.getNovelId());
        assertEquals(expectedPossessionCount, actualTicket.getPossessionCount());
        assertEquals(expectedRentalCount, actualTicket.getRentalCount());
    }*/
}
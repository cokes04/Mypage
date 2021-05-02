package com.kog.mypage.novel.service;

import com.kog.mypage.novel.dto.AddTicketDto;
import com.kog.mypage.novel.dto.UseTicketDto;
import com.kog.mypage.novel.entity.Episode;
import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.enumerate.TicketType;
import com.kog.mypage.novel.entity.ticket.AddTicketRecord;
import com.kog.mypage.novel.entity.ticket.Ticket;
import com.kog.mypage.novel.entity.ticket.TicketRecord;
import com.kog.mypage.novel.entity.ticket.UseTicketRecord;
import com.kog.mypage.novel.repository.TicketRecordRepository;
import com.kog.mypage.novel.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TicketServiceImpl implements TicketService{
    public final int RENTAL_HOUR = 3 * 24;
    public final int TICKET_RECORD_PAGE_SIZE = 50;
    public final Sort TICKET_RECORD_PAGE_SORT = Sort.by(Sort.Direction.ASC, "createdDate");

    private final TicketRepository ticketRepository;
    private final TicketRecordRepository ticketRecordRepository;

    @Transactional
    @Override
    public Optional<Ticket> getTicketByUserIdAndNovelId(Long userId, Long novelId){
        return ticketRepository.findByUser_IdAndNovel_Id(userId, novelId);
    }

    @Transactional
    @Override
    public Page<AddTicketRecord> getAddTicketRecordByUserIdAndNovelId(Long userId, Optional<Long> optionalNovelId, int pageNum) {
        PageRequest pageRequest = PageRequest.of(pageNum,TICKET_RECORD_PAGE_SIZE, TICKET_RECORD_PAGE_SORT);
        if (optionalNovelId.isPresent())
            return ticketRecordRepository.findAddTicketRecord(userId, optionalNovelId.orElseThrow(), pageRequest);
        else
            return ticketRecordRepository.findAddTicketRecord(userId, pageRequest);

    }

    @Transactional
    @Override
    public Page<UseTicketRecord> getUseTicketRecordByUserIdAndNovelId(Long userId, Optional<Long> optionalNovelId, int pageNum) {
        PageRequest pageRequest = PageRequest.of(pageNum,TICKET_RECORD_PAGE_SIZE, TICKET_RECORD_PAGE_SORT);
        if (optionalNovelId.isPresent())
            return ticketRecordRepository.findUseTicketRecord(userId, optionalNovelId.orElseThrow(), pageRequest);
        else
            return ticketRecordRepository.findUseTicketRecord(userId, pageRequest);
    }

    @Transactional
    @Override
    public Ticket useTicket(UseTicketDto ticketDto){
        ticketRecordRepository.save(ticketDto.createTicketRecord());
        if(isPurchasedEpisode(ticketDto.getUser(), ticketDto.getEpisode())){ // 이미 보유중인 에피소드면 예외
                throw new RuntimeException("이미 구매한 에피소드");
            }
        Optional<Ticket> optionalTicket = ticketRepository.findByUserAndNovel(ticketDto.getUser(),ticketDto.getNovel());

        if(optionalTicket.isPresent()){
            Ticket ticket = optionalTicket.orElseThrow();
            ticket.changeCount(-1, ticketDto.getTicketType());
            return ticket;
        }
        else{
            throw new RuntimeException("소유중인 티켓 없음");
        }
    }

    @Transactional
    @Override
    public Ticket addTicket(AddTicketDto ticketDto){
        ticketRecordRepository.save(ticketDto.createTicketRecord());
        Optional<Ticket> optionalTicket = ticketRepository.findByUserAndNovel(ticketDto.getUser(),ticketDto.getNovel());

        if(optionalTicket.isPresent()){
            Ticket ticket = optionalTicket.orElseThrow();
            ticket.changeCount(ticketDto.getCount(), ticketDto.getTicketType());
            return ticket;
        }
        else{ // 없으면 새로 만듬
            return ticketRepository.save(ticketDto.createTicket());
        }
    }

    @Transactional
    @Override
    public Ticket cancelTicket(AddTicketDto ticketDto) {
        ticketRecordRepository.save(ticketDto.createTicketRecord());
        Optional<Ticket> optionalTicket = ticketRepository.findByUserAndNovel(ticketDto.getUser(),ticketDto.getNovel());

        if(optionalTicket.isPresent()){
            Ticket ticket = optionalTicket.orElseThrow();
            ticket.changeCount(ticketDto.getCount(), ticketDto.getTicketType());
            return ticket;
        }
        else{ // 없으면 에러
            throw new RuntimeException("없는 티켓 취소");
        }
    }

    @Transactional
    @Override
    public Ticket createNewTicket(User user, Novel novel) {
        Ticket newTicket = Ticket.builder()
                .user(user).novel(novel).rentalCount(0).possessionCount(0)
                .build();
        return ticketRepository.save(newTicket);
    }

    // 소유, 대여중인 에피소드인지 확인
    private boolean isPurchasedEpisode(User user, Episode episode) {
        List<UseTicketRecord> useRecords = ticketRecordRepository.findByUserAndEpisode(user, episode);
        for (UseTicketRecord useReocrd : useRecords) {
            if (useReocrd.getTicketType() == TicketType.POSSESSION) {
                return true; // 소유중인 에피소드
            } else if (useReocrd.getCreatedDate().plusHours(RENTAL_HOUR).isAfter(LocalDateTime.now())) {// 대여 끝났는지 확인;
                return true; // 대여중인 에피소드
            }
        }
        return false; //대여중 아니고 소유도 하지 않는 에피소드
    }

}


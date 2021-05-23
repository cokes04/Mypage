package com.kog.mypage.ticket.service;

import com.kog.mypage.ticket.dto.AddTicketDto;
import com.kog.mypage.ticket.dto.UseTicketDto;
import com.kog.mypage.ticket.dto.UserInfoDto;
import com.kog.mypage.ticket.entity.AddTicketRecord;
import com.kog.mypage.ticket.entity.TicketRecord;
import com.kog.mypage.ticket.entity.UseTicketRecord;
import com.kog.mypage.ticket.entity.enumerate.TicketType;
import com.kog.mypage.ticket.repository.TicketRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketRecordServiceImpl implements TicketRecordService {

    public final int RENTAL_HOUR = 3 * 24;
    public final int TICKET_RECORD_PAGE_SIZE = 50;
    public final Sort TICKET_RECORD_PAGE_SORT = Sort.by(Sort.Direction.ASC, "createdDate");

    private final TicketRecordRepository ticketRecordRepository;

    @Override
    public AddTicketRecord createAddTicketRecord(AddTicketDto addTicketDto, UserInfoDto userInfoDto) {
        AddTicketRecord addTicketRecord = AddTicketRecord.builder()
                .userId(userInfoDto.getUserId())
                .novelId(addTicketDto.getNovelId())
                .ticketType(addTicketDto.getTicketType())
                .count(addTicketDto.getCount())
                .paidCount(addTicketDto.getPaidCount())
                .price(addTicketDto.getPrice())
                .build();
        return ticketRecordRepository.save(addTicketRecord);
    }

    @Override
    public UseTicketRecord createUseTicketRecord(UseTicketDto useTicketDto, UserInfoDto userInfoDto) {
        UseTicketRecord useTicketRecord = UseTicketRecord.builder()
                .userId(userInfoDto.getUserId())
                .novelId(useTicketDto.getNovelId())
                .episodeId(useTicketDto.getEpisodeId())
                .ticketType(useTicketDto.getTicketType())
                .build();

        return ticketRecordRepository.save(useTicketRecord);
    }

    @Override
    public Page<AddTicketRecord> getAddTicketRecordByNovelId(Optional<Long> optionalNovelId, int pageNum, UserInfoDto userInfoDto) {
        Long userId = userInfoDto.getUserId();

        Pageable pageable = PageRequest.of(pageNum,TICKET_RECORD_PAGE_SIZE, TICKET_RECORD_PAGE_SORT);
        if (optionalNovelId.isPresent())
            return ticketRecordRepository.findAddTicketRecord(userId, optionalNovelId.orElseThrow(), pageable);
        else
            return ticketRecordRepository.findAddTicketRecord(userId, pageable);
    }

    @Override
    public Page<UseTicketRecord> getUseTicketRecordByNovelId(Optional<Long> optionalNovelId, int pageNum, UserInfoDto userInfoDto) {
        Long userId = userInfoDto.getUserId();

        PageRequest pageRequest = PageRequest.of(pageNum,TICKET_RECORD_PAGE_SIZE, TICKET_RECORD_PAGE_SORT);
        if (optionalNovelId.isPresent())
            return ticketRecordRepository.findUseTicketRecord(userId, optionalNovelId.orElseThrow(), pageRequest);
        else
            return ticketRecordRepository.findUseTicketRecord(userId, pageRequest);
    }

    @Override
    public boolean isPurchasedEpisode(Long episodeId, UserInfoDto userInfoDto) {
        List<UseTicketRecord> useRecords = ticketRecordRepository.findByEpisodeIdAndUserId(episodeId, userInfoDto.getUserId());
        for (UseTicketRecord useReocrd : useRecords) {
            if (useReocrd.getTicketType() == TicketType.POSSESSION) {
                return true; // 소유중인 에피소드
            } else if (useReocrd.getCreatedDate().plusHours(RENTAL_HOUR).isAfter(LocalDateTime.now())) {// 대여 끝났는지 확인
                return true; // 대여중인 에피소드
            }
        }
        return false; //대여중 아니고 소유도 하지 않는 에피소드
    }

}

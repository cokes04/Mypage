package com.kog.mypage.ticket.service;

import com.kog.mypage.ticket.dto.AddTicketDto;
import com.kog.mypage.ticket.dto.UseTicketDto;
import com.kog.mypage.ticket.entity.AddTicketRecord;
import com.kog.mypage.ticket.entity.UseTicketRecord;
import com.kog.mypage.ticket.enumeration.TicketType;
import com.kog.mypage.ticket.repository.TicketRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketRecordServiceImpl implements TicketRecordService {

    public final int RENTAL_HOUR = 3 * 24;
    private final TicketRecordRepository ticketRecordRepository;

    @Override
    public AddTicketRecord createAddTicketRecord(AddTicketDto addTicketDto) {
        AddTicketRecord addTicketRecord = AddTicketRecord.builder()
                .userId(addTicketDto.getUserId())
                .novelId(addTicketDto.getNovelId())
                .ticketType(addTicketDto.getTicketType())
                .count(addTicketDto.getCount())
                .paidCount(addTicketDto.getPaidCount())
                .price(addTicketDto.getPrice())
                .build();
        return ticketRecordRepository.save(addTicketRecord);
    }

    @Override
    public UseTicketRecord createUseTicketRecord(UseTicketDto useTicketDto) {
        UseTicketRecord useTicketRecord = UseTicketRecord.builder()
                .userId(useTicketDto.getUserId())
                .novelId(useTicketDto.getNovelId())
                .episodeId(useTicketDto.getEpisodeId())
                .ticketType(useTicketDto.getTicketType())
                .build();

        return ticketRecordRepository.save(useTicketRecord);
    }

    @Override
    public Page<AddTicketRecord> getAddTicketRecordByNovelId(Long novelId, Long userId, Pageable pageable) {
        return ticketRecordRepository.findAddTicketRecord(userId, novelId, pageable);
    }

    @Override
    public Page<UseTicketRecord> getUseTicketRecordByNovelId(Long novelId, Long userId, Pageable pageable) {
        return ticketRecordRepository.findUseTicketRecord(userId, novelId, pageable);
    }

    @Override
    public boolean isPurchasedEpisode(Long episodeId, Long userId) {
        List<UseTicketRecord> useRecords = ticketRecordRepository.findByEpisodeIdAndUserId(episodeId, userId);
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

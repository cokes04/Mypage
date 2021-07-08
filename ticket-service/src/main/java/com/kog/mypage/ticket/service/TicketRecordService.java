package com.kog.mypage.ticket.service;

import com.kog.mypage.ticket.dto.AddTicketDto;
import com.kog.mypage.ticket.dto.UseTicketDto;
import com.kog.mypage.ticket.entity.AddTicketRecord;
import com.kog.mypage.ticket.entity.UseTicketRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface TicketRecordService {

    AddTicketRecord createAddTicketRecord(AddTicketDto addTicketDto);

    UseTicketRecord createUseTicketRecord(UseTicketDto useTicketDto);

    Page<AddTicketRecord> getAddTicketRecordByNovelId(Long novelId, Long userId, Pageable pageable);

    Page<UseTicketRecord> getUseTicketRecordByNovelId(Long novelId, Long userId, Pageable pageable);

    boolean isPurchasedEpisode(Long episodeId, Long userId);
}

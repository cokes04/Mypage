package com.kog.mypage.ticket.service;

import com.kog.mypage.ticket.dto.AddTicketDto;
import com.kog.mypage.ticket.dto.UseTicketDto;
import com.kog.mypage.ticket.dto.UserInfoDto;
import com.kog.mypage.ticket.entity.AddTicketRecord;
import com.kog.mypage.ticket.entity.UseTicketRecord;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface TicketRecordService {

    AddTicketRecord createAddTicketRecord(AddTicketDto addTicketDto, UserInfoDto userInfoDto);

    UseTicketRecord createUseTicketRecord(UseTicketDto useTicketDto, UserInfoDto userInfoDto);

    Page<AddTicketRecord> getAddTicketRecordByNovelId(Optional<Long> optionalNovelId, int pageNum, UserInfoDto userInfoDto);

    Page<UseTicketRecord> getUseTicketRecordByNovelId(Optional<Long> optionalNovelId, int pageNum, UserInfoDto userInfoDto);

    boolean isPurchasedEpisode(Long episodeId, UserInfoDto userInfoDto);
}

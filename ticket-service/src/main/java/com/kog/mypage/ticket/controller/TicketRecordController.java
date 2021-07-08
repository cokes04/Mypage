package com.kog.mypage.ticket.controller;

import com.kog.mypage.ticket.entity.AddTicketRecord;
import com.kog.mypage.ticket.entity.UseTicketRecord;
import com.kog.mypage.ticket.enumeration.TicketRecordSort;
import com.kog.mypage.ticket.payload.request.TicketRecordPageRequest;
import com.kog.mypage.ticket.payload.request.UserInfo;
import com.kog.mypage.ticket.payload.response.AddTicketRecordListResponse;
import com.kog.mypage.ticket.payload.response.UseTicketRecordListResponse;
import com.kog.mypage.ticket.service.TicketRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ticket-record")
public class TicketRecordController {

    private final TicketRecordService ticketRecordService;

    @GetMapping("/add")
    public ResponseEntity<?> getAddTicketRecord(@Validated @RequestParam(name = "novelId") Long novelId,
                                                @Validated UserInfo userInfoDto,
                                                @Validated TicketRecordPageRequest pageRequest){

        int page = pageRequest.getPage();
        int size = pageRequest.getSize();
        Sort sort = TicketRecordSort.valueOf(pageRequest.getStandard().toUpperCase()).getSort();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<AddTicketRecord> ticketRecords = ticketRecordService.getAddTicketRecordByNovelId(novelId, userInfoDto.getUserId(), pageable);
        return ResponseEntity.ok().body(AddTicketRecordListResponse.of(true, "성공", ticketRecords));
    }

    @GetMapping("/use")
    public ResponseEntity<?> getUseTicketRecord(@Validated @RequestParam(name = "novelId") Long novelId,
                                                @Validated UserInfo userInfoDto,
                                                @Validated TicketRecordPageRequest pageRequest){

        int page = pageRequest.getPage();
        int size = pageRequest.getSize();
        Sort sort = TicketRecordSort.valueOf(pageRequest.getStandard().toUpperCase()).getSort();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<UseTicketRecord> ticketRecords = ticketRecordService.getUseTicketRecordByNovelId(novelId, userInfoDto.getUserId(), pageable);
        return ResponseEntity.ok().body(UseTicketRecordListResponse.of(true, "성공", ticketRecords));
    }
}

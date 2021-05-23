package com.kog.mypage.ticket.controller;

import com.kog.mypage.ticket.dto.UserInfoDto;
import com.kog.mypage.ticket.entity.AddTicketRecord;
import com.kog.mypage.ticket.entity.Ticket;
import com.kog.mypage.ticket.entity.UseTicketRecord;
import com.kog.mypage.ticket.payload.request.TicketRecordRequest;
import com.kog.mypage.ticket.payload.request.TicketRequest;
import com.kog.mypage.ticket.payload.response.AddTicketRecordListResponse;
import com.kog.mypage.ticket.payload.response.ApiResponse;
import com.kog.mypage.ticket.payload.response.TicketResponse;
import com.kog.mypage.ticket.payload.response.UseTicketRecordListResponse;
import com.kog.mypage.ticket.service.TicketRecordService;
import com.kog.mypage.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;
    private final TicketRecordService ticketRecordService;

    @PostMapping
    public ResponseEntity<?> getTikcet(@Validated @RequestBody TicketRequest ticketRequest) {
        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(ticketRequest.getUserInfo().getUserId())
                .roles(ticketRequest.getUserInfo().getRoles())
                .build();

        Long novelId =  ticketRequest.getNovelId();
        Ticket ticket;
        try {
            ticket = ticketService.getTicketByNovelId(novelId, userInfoDto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false,"실패"));
        }

        TicketResponse ticketResponse = TicketResponse.of(true, "성공", ticket);
        return ResponseEntity.ok().body(ticketResponse);
    }

    @PostMapping("/addRecord")
    public ResponseEntity<?> getAddTicketRecord(@Validated @RequestBody TicketRecordRequest ticketRecordRequest){
        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(ticketRecordRequest.getUserInfo().getUserId())
                .roles(ticketRecordRequest.getUserInfo().getRoles())
                .build();

        int pageNum = ticketRecordRequest.getPageNum();
        Optional<Long> optionalNovelId = ticketRecordRequest.getNovelId();  // novelId가 null 이면 해당 유저의 AddTicketRecord 전체

        Page<AddTicketRecord> ticketRecords = ticketRecordService.getAddTicketRecordByNovelId(optionalNovelId, pageNum, userInfoDto);
        return ResponseEntity.ok().body(AddTicketRecordListResponse.of(true, "성공", ticketRecords));
    }

    @PostMapping("/useRecord")
    public ResponseEntity<?> getUseTicketRecord(@Validated @RequestBody TicketRecordRequest ticketRecordRequest){
        UserInfoDto userInfoDto = UserInfoDto.builder()
                .userId(ticketRecordRequest.getUserInfo().getUserId())
                .roles(ticketRecordRequest.getUserInfo().getRoles())
                .build();

        int pageNum = ticketRecordRequest.getPageNum();
        Optional<Long> optionalNovelId = ticketRecordRequest.getNovelId(); // novelId가 null 이면 해당 유저의 UseTicketRecord 전체

        Page<UseTicketRecord> ticketRecords = ticketRecordService.getUseTicketRecordByNovelId(optionalNovelId, pageNum, userInfoDto);

        return ResponseEntity.ok().body(UseTicketRecordListResponse.of(true, "성공", ticketRecords));
    }
}

package com.kog.mypage.ticket.controller;

import com.kog.mypage.ticket.dto.AddTicketDto;
import com.kog.mypage.ticket.dto.UseTicketDto;
import com.kog.mypage.ticket.entity.Ticket;
import com.kog.mypage.ticket.enumeration.TicketType;
import com.kog.mypage.ticket.payload.request.AddTicketRequest;
import com.kog.mypage.ticket.payload.request.UseTicketRequest;
import com.kog.mypage.ticket.payload.request.UserInfo;
import com.kog.mypage.ticket.payload.response.TicketResponse;
import com.kog.mypage.ticket.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RequiredArgsConstructor
@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping
    public ResponseEntity<?> getTikcetOfNovel(@Validated @RequestParam(name = "novelId") Long novelId,
                                              @Validated UserInfo userInfo) {
        Long userId = userInfo.getUserId();

        Ticket ticket = ticketService.getTicketByNovelIdAndUserId(novelId, userId);

        return ResponseEntity.ok()
                .body(TicketResponse.of(true, "성공", ticket));
    }

    @PatchMapping("/add")
    public ResponseEntity<?> addTicket(@Validated @RequestBody AddTicketRequest addTicketRequest){
        AddTicketDto addTicketDto = AddTicketDto.builder()
                .userId(addTicketRequest.getUserInfo().getUserId())
                .novelId(addTicketRequest.getNovelId())
                .ticketType(TicketType.toEnum(addTicketRequest.getType().toUpperCase()))
                .count(addTicketRequest.getCount())
                .paidCount(addTicketRequest.getPaidCount())
                .price(addTicketRequest.getPrice())
                .build();

        ticketService.addTicket(addTicketDto);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/use")
    public ResponseEntity<?> useTicket(@Validated  @RequestBody UseTicketRequest useTicketRequest){
        UseTicketDto useTicketDto = UseTicketDto.builder()
                .userId(useTicketRequest.getUserInfo().getUserId())
                .novelId(useTicketRequest.getNovelId())
                .ticketType(TicketType.toEnum(useTicketRequest.getType().toUpperCase()))
                .episodeId(useTicketRequest.getEpisodeId())
                .build();

        ticketService.useTicket(useTicketDto);

        return ResponseEntity.noContent().build();
    }
}

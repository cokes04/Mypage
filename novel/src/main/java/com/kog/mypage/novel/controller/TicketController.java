package com.kog.mypage.novel.controller;

import com.kog.mypage.novel.entity.Novel;
import com.kog.mypage.novel.entity.User;
import com.kog.mypage.novel.entity.ticket.AddTicketRecord;
import com.kog.mypage.novel.entity.ticket.Ticket;
import com.kog.mypage.novel.entity.ticket.UseTicketRecord;
import com.kog.mypage.novel.payload.request.TicketRecordRequest;
import com.kog.mypage.novel.payload.response.AddTicketRecordListResponse;
import com.kog.mypage.novel.payload.response.ApiResponse;
import com.kog.mypage.novel.payload.response.TicketResponse;
import com.kog.mypage.novel.payload.response.UseTicketRecordListResponse;
import com.kog.mypage.novel.security.TokenProvider;
import com.kog.mypage.novel.service.NovelService;
import com.kog.mypage.novel.service.TicketService;
import com.kog.mypage.novel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/ticket")
public class TicketController {

    private final TicketService ticketService;

    private final NovelService novelService;

    private final UserService userService;

    private final TokenProvider tokenProvider;

    @PostMapping
    public ResponseEntity<?> getTikcet(@RequestHeader(name="Authorization") String token, @RequestBody Map<String,Long> map){
        Long userId = tokenProvider.getUserId(token);
        Long novelId = map.get("novelId");

        Optional<Ticket> optionalTicket = ticketService.getTicketByUserIdAndNovelId(userId ,novelId);
        Ticket ticket;
        if(optionalTicket.isPresent()){
            ticket = optionalTicket.orElseThrow();
        }else {
            try {
                User user = userService.getUserById(userId).orElseThrow( () -> new RuntimeException("존재하지 않는 USER ID"));
                Novel novel = novelService.getNovel(novelId).orElseThrow( () -> new RuntimeException("존재하지 않는 NOVEL ID"));
                ticket = ticketService.createNewTicket(user, novel);
            }catch (Exception e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(false,"실패"));
            }
        }

        TicketResponse ticketResponse = TicketResponse.of(true, "성공", ticket);
        return ResponseEntity.ok().body(ticketResponse);
    }

    @PostMapping("/addRecord")
    public ResponseEntity<?> getAddTicketRecord(@RequestHeader(name="Authorization") String token,
                                                @Validated @RequestBody TicketRecordRequest ticketRecordRequest){
        Long userId = tokenProvider.getUserId(token);
        int pageNum = ticketRecordRequest.getPageNum();
        Optional<Long> optionalNovelId = ticketRecordRequest.getNovelId();  // null 이면 해당 유저의 AddTicketRecord 전체

        Page<AddTicketRecord> ticketRecords = ticketService.getAddTicketRecordByUserIdAndNovelId(userId, optionalNovelId, pageNum);
        return ResponseEntity.ok().body(AddTicketRecordListResponse.of(true, "성공", ticketRecords));
    }

    @PostMapping("/useRecord")
    public ResponseEntity<?> getUseTicketRecord(@RequestHeader(name="Authorization") String token,
                                                @Validated @RequestBody TicketRecordRequest ticketRecordRequest){
        Long userId = tokenProvider.getUserId(token);
        int pageNum = ticketRecordRequest.getPageNum();
        Optional<Long> optionalNovelId = ticketRecordRequest.getNovelId(); // null 이면 해당 유저의 UseTicketRecord 전체

        Page<UseTicketRecord> ticketRecords = ticketService.getUseTicketRecordByUserIdAndNovelId(userId, optionalNovelId, pageNum);

        return ResponseEntity.ok().body(UseTicketRecordListResponse.of(true, "성공", ticketRecords));
    }
}

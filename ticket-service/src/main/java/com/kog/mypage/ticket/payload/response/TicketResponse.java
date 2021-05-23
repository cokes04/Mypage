package com.kog.mypage.ticket.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kog.mypage.ticket.entity.Ticket;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class TicketResponse extends ApiResponse {

    private Long tiketId;

    private Long userId;

    private Long novelId;

    private int possessionCount;

    private int rentCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createDate;


    @Builder
    private TicketResponse(boolean success, String message, Long tiketId, Long userId, Long novelId,
                           int possessionCount, int rentCount, LocalDateTime createDate) {
        super(success, message);
        this.tiketId = tiketId;
        this.userId = userId;
        this.novelId = novelId;
        this.possessionCount = possessionCount;
        this.rentCount = rentCount;
        this.createDate = createDate;
    }

    public static TicketResponse of(boolean success, String message, Ticket ticket){
        return TicketResponse.builder()
                .success(success)
                .message(message)
                .tiketId(ticket.getId())
                .userId(ticket.getUserId())
                .novelId(ticket.getNovelId())
                .possessionCount(ticket.getPossessionCount())
                .rentCount(ticket.getRentalCount())
                .createDate(ticket.getCreateDate())
                .build();
    }
}

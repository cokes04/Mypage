package com.kog.mypage.ticket.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kog.mypage.ticket.entity.Ticket;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class TicketResponse extends ApiResponse {

    private Long userId;

    private Long novelId;

    private int possessionCount;

    private int rentCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdDate;


    @Builder
    private TicketResponse(boolean success, String message, Long userId, Long novelId,
                           int possessionCount, int rentCount, LocalDateTime createdDate) {
        super(success, message);
        this.userId = userId;
        this.novelId = novelId;
        this.possessionCount = possessionCount;
        this.rentCount = rentCount;
        this.createdDate = createdDate;
    }

    public static TicketResponse of(boolean success, String message, Ticket ticket){
        return TicketResponse.builder()
                .success(success)
                .message(message)
                .userId(ticket.getUserId())
                .novelId(ticket.getNovelId())
                .possessionCount(ticket.getPossessionCount())
                .rentCount(ticket.getRentalCount())
                .createdDate(ticket.getCreatedDate())
                .build();
    }
}

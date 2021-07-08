package com.kog.mypage.ticket.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kog.mypage.ticket.entity.Ticket;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public class TicketListResponse extends ApiResponse{

    private List<TicketResponse> ticketList;

    private Long totalEpisodeCount;

    private int totalEpisodePage;

    @Builder
    private TicketListResponse(boolean success, String message, List<TicketResponse> ticketList,
                                           Long totalEpisodeCount, int totalEpisodePage) {
        super(success, message);
        this.ticketList = ticketList;
        this.totalEpisodeCount = totalEpisodeCount;
        this.totalEpisodePage = totalEpisodePage;
    }

    public static TicketListResponse of(boolean success, String message, Page<Ticket> tickets){
        return TicketListResponse.builder()
                .success(success)
                .message(message)
                .ticketList(tickets.map(TicketResponse::of).toList())
                .totalEpisodeCount(tickets.getTotalElements())
                .totalEpisodePage(tickets.getTotalPages())
                .build();
    }

    @Getter
    private static class TicketResponse{

        private Long tiketId;

        private Long userId;

        private Long novelId;

        private int possessionCount;

        private int rentCount;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createDate;

        @Builder
        public TicketResponse(Long tiketId, Long userId, Long novelId, int possessionCount, int rentCount, LocalDateTime createDate) {
            this.tiketId = tiketId;
            this.userId = userId;
            this.novelId = novelId;
            this.possessionCount = possessionCount;
            this.rentCount = rentCount;
            this.createDate = createDate;
        }

        public static TicketResponse of(Ticket ticket){
            return TicketResponse.builder()
                    .tiketId(ticket.getId())
                    .userId(ticket.getUserId())
                    .novelId(ticket.getNovelId())
                    .possessionCount(ticket.getPossessionCount())
                    .rentCount(ticket.getRentalCount())
                    .createDate(ticket.getCreatedDate())
                    .build();
        }
    }
}

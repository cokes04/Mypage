package com.kog.mypage.novel.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kog.mypage.novel.entity.ticket.UseTicketRecord;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UseTicketRecordListResponse extends ApiResponse{

    private List<UseTicketRecordResponse> useTicketRecordList;

    @Builder
    private UseTicketRecordListResponse(boolean success, String message, List<UseTicketRecordResponse> useTicketRecordList) {
        super(success, message);
        this.useTicketRecordList = useTicketRecordList;
    }

    public static UseTicketRecordListResponse of(boolean success, String message , Page<UseTicketRecord> useTicketRecordList){
        return UseTicketRecordListResponse.builder()
                .success(success)
                .message(message)
                .useTicketRecordList( useTicketRecordList.map(UseTicketRecordResponse::new).toList() )
                .build();
    }

    @Getter
    private static class UseTicketRecordResponse {
        private Long recordId;

        private String paidTicket;

        private String ticketType;

        private Long userId;

        private Long novelId;

        private String novelTitle;

        private String genre;

        private String episodeTitle;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdDate;

        @Builder
        public UseTicketRecordResponse(UseTicketRecord useTicketRecord) {
            this.recordId = useTicketRecord.getId();
            this.paidTicket = useTicketRecord.isPaidTicket() ? "Y" : "N";
            this.ticketType = useTicketRecord.getTicketType().value;
            this.userId = useTicketRecord.getUser().getId();
            this.novelId = useTicketRecord.getNovel().getId();
            this.novelTitle = useTicketRecord.getNovel().getTitle();
            this.genre = useTicketRecord.getNovel().getGenre().value;
            this.createdDate = useTicketRecord.getCreatedDate();
            this.episodeTitle = useTicketRecord.getEpisode().getTitle();
        }
    }
}

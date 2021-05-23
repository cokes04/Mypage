package com.kog.mypage.ticket.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kog.mypage.ticket.entity.UseTicketRecord;
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

        private Long episodeId;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdDate;

        @Builder
        public UseTicketRecordResponse(UseTicketRecord useTicketRecord) {
            this.recordId = useTicketRecord.getId();
            this.paidTicket = useTicketRecord.isPaidTicket() ? "Y" : "N";
            this.ticketType = useTicketRecord.getTicketType().value;
            this.userId = useTicketRecord.getUserId();
            this.novelId = useTicketRecord.getNovelId();
            this.createdDate = useTicketRecord.getCreatedDate();
            this.episodeId = useTicketRecord.getEpisodeId();
        }
    }
}

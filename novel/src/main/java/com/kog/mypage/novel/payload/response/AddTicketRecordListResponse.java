package com.kog.mypage.novel.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kog.mypage.novel.entity.ticket.AddTicketRecord;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AddTicketRecordListResponse extends ApiResponse{

    private List<AddTicketRecordResponse> addTicketRecordList;

    @Builder
    private AddTicketRecordListResponse(boolean success, String message, List<AddTicketRecordResponse> addTicketRecordList) {
        super(success, message);
        this.addTicketRecordList = addTicketRecordList;
    }

    public static AddTicketRecordListResponse of(boolean success, String message , Page<AddTicketRecord> addTicketRecords){
        return AddTicketRecordListResponse.builder()
                .success(success)
                .message(message)
                .addTicketRecordList( addTicketRecords.map( AddTicketRecordResponse::new).toList() )
                .build();
    }

    @Getter
    private static class AddTicketRecordResponse {
         private Long recordId;

         private int price;

         private int paidCount;

         private int ticketCount;

         private String ticketType;

         private Long userId;

         private Long novelId;

         private String novelTitle;

         private String genre;

         @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
         private LocalDateTime createdDate;

         @Builder
         private AddTicketRecordResponse(AddTicketRecord addTicketRecord) {
             this.recordId = addTicketRecord.getId();
             this.price = addTicketRecord.getPrice();
             this.paidCount = addTicketRecord.getPaidCount();
             this.ticketCount = addTicketRecord.getCount();
             this.ticketType = addTicketRecord.getTicketType().value;
             this.userId = addTicketRecord.getUser().getId();
             this.novelId = addTicketRecord.getNovel().getId();
             this.novelTitle = addTicketRecord.getNovel().getTitle();
             this.genre = addTicketRecord.getNovel().getGenre().value;
             this.createdDate = addTicketRecord.getCreatedDate();
         }
     }
}

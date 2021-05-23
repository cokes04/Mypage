package com.kog.mypage.cash.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kog.mypage.cash.entity.AddCashRecord;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AddCashRecordListResponse extends ApiResponse{
    List<AddCashRecordResponse> useCashRecordResponses;

    @Builder
    public AddCashRecordListResponse(boolean success, String message, List<AddCashRecordResponse> useCashRecordResponses) {
        super(success, message);
        this.useCashRecordResponses = useCashRecordResponses;
    }

    public static AddCashRecordListResponse of(boolean success, String message, Page<AddCashRecord> AddCashRecords){
        return AddCashRecordListResponse.builder()
                .success(success)
                .message(message)
                .useCashRecordResponses( AddCashRecords.map( AddCashRecordResponse :: new).toList())
                .build();
    }

    private static class AddCashRecordResponse{

        private Long userId;

        private Long recordId;

        private int freeAmount;

        private int paidAmount;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdDate;

        public AddCashRecordResponse(AddCashRecord addCashRecord) {
            this.userId = addCashRecord.getUserId();
            this.recordId = addCashRecord.getId();
            this.freeAmount = addCashRecord.getFreeAmount();
            this.paidAmount = addCashRecord.getPaidAmount();
            this.createdDate = addCashRecord.getCreatedDate();

        }
    }
}
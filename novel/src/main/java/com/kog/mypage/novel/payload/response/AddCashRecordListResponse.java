package com.kog.mypage.novel.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kog.mypage.novel.entity.cash.UseCashRecord;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AddCashRecordListResponse extends ApiResponse{
    List<UseCashRecordResponse> useCashRecordResponses;

    @Builder
    public AddCashRecordListResponse(boolean success, String message, List<UseCashRecordResponse> useCashRecordResponses) {
        super(success, message);
        this.useCashRecordResponses = useCashRecordResponses;
    }

    public static AddCashRecordListResponse of(boolean success, String message, Page<UseCashRecord> useCashRecords){
        return AddCashRecordListResponse.builder()
                .success(success)
                .message(message)
                .useCashRecordResponses( useCashRecords.map( UseCashRecordResponse :: new).toList())
                .build();
    }

    private static class UseCashRecordResponse{

        private Long userId;

        private Long recordId;

        private Long novelId;

        private int amount;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdDate;

        public UseCashRecordResponse(UseCashRecord useCashRecord) {

        }
    }
}
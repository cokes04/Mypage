package com.kog.mypage.cash.payload.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.kog.mypage.cash.entity.UseCashRecord;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class UseCashRecordListResponse extends ApiResponse{
    List<UseCashRecordResponse> useCashRecordResponses;

    @Builder
    public UseCashRecordListResponse(boolean success, String message, List<UseCashRecordResponse> useCashRecordResponses) {
        super(success, message);
        this.useCashRecordResponses = useCashRecordResponses;
    }

    public static UseCashRecordListResponse of(boolean success, String message, Page<UseCashRecord> useCashRecords){
        return UseCashRecordListResponse.builder()
                .success(success)
                .message(message)
                .useCashRecordResponses( useCashRecords.map( UseCashRecordResponse :: new).toList())
                .build();
    }

    private static class UseCashRecordResponse{

        private Long userId;

        private Long recordId;

        private Long novelId;

        private int freeAmount;

        private int paidAmount;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdDate;

        public UseCashRecordResponse(UseCashRecord useCashRecord) {
            this.userId = useCashRecord.getUserId();
            this.recordId = useCashRecord.getId();
            this.novelId = useCashRecord.getNovelId();

            this.freeAmount = useCashRecord.getFreeAmount();
            this.paidAmount = useCashRecord.getPaidAmount();

            this.createdDate = useCashRecord.getCreatedDate();

        }
    }
}

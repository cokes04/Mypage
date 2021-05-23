package com.kog.mypage.cash.payload.response;

import com.kog.mypage.cash.entity.Cash;
import lombok.Builder;
import lombok.Getter;


@Getter
public class CashResponse extends ApiResponse {


    private Long userId;

    private int paidAmount;

    private int freeAmount;

    private int totalAmount;

    @Builder
    private CashResponse(boolean success, String message, Long userId, int paidAmount, int freeAmount, int totalAmount) {
        super(success, message);
        this.userId = userId;
        this.paidAmount = paidAmount;
        this.freeAmount = freeAmount;
        this.totalAmount = totalAmount;
    }

    public static CashResponse of(boolean success, String message, Cash cash){
        return CashResponse.builder()
                .success(success)
                .message(message)
                .userId(cash.getUserId())
                .paidAmount(cash.getPaidAmount())
                .freeAmount(cash.getFreeAmount())
                .totalAmount(cash.getTotalAmount())
                .build();

    }
}

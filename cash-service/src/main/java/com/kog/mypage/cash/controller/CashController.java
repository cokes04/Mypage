package com.kog.mypage.cash.controller;

import com.kog.mypage.cash.entity.AddCashRecord;
import com.kog.mypage.cash.entity.Cash;
import com.kog.mypage.cash.entity.UseCashRecord;
import com.kog.mypage.cash.payload.request.CashRecordReuqest;
import com.kog.mypage.cash.payload.response.AddCashRecordListResponse;
import com.kog.mypage.cash.payload.response.CashResponse;
import com.kog.mypage.cash.payload.response.UseCashRecordListResponse;
import com.kog.mypage.cash.service.CashService;
import com.kog.mypage.cash.payload.request.AuthorizationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cash")
public class CashController {
    private final CashService cashService;

    @GetMapping
    public ResponseEntity<?> getCash(@Validated @RequestBody AuthorizationInfo authorizationInfo){
        Long userId = authorizationInfo.getId();
        Cash cash = cashService.getCashByUserId(userId);

        return ResponseEntity.ok().body(CashResponse.of(true, "성공", cash));
    }

    @GetMapping("/useReocord")
    public ResponseEntity<?> getUseReocord(@Validated @RequestBody CashRecordReuqest cashRecordReuqest){ // pageNum 검증해야
        Long userId = cashRecordReuqest.getId();
        int pageNum = cashRecordReuqest.getPageNum();
        Page<UseCashRecord> useCashRecords =  cashService.getUseCashRecordByUserId(userId, pageNum);
        return ResponseEntity.ok().
                body(UseCashRecordListResponse.of(true, "성공", useCashRecords));

    }

    @GetMapping("/addReocord")
    public ResponseEntity<?> getAddReocord(@Validated @RequestBody CashRecordReuqest cashRecordReuqest){ // pageNum 검증해야
        Long userId = cashRecordReuqest.getId();
        int pageNum = cashRecordReuqest.getPageNum();
        Page<AddCashRecord> addCashRecords =  cashService.getAddCashRecordByUserId(userId, pageNum);
        return ResponseEntity.ok().
                body(AddCashRecordListResponse.of(true, "성공", addCashRecords));

    }
}

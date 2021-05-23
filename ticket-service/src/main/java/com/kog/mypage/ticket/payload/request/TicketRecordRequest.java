package com.kog.mypage.ticket.payload.request;

import lombok.Getter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;
import java.util.Optional;

@Getter
public class TicketRecordRequest {

    @PositiveOrZero
    private int pageNum;

    @Max( value = Long.MAX_VALUE )
    @Min( value = 0 )
    private Long novelId;


    private UserInfo userInfo;

    public Optional<Long> getNovelId(){
        return Optional.ofNullable(novelId);
    }

}

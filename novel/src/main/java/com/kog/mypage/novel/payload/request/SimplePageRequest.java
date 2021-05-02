package com.kog.mypage.novel.payload.request;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.constraints.*;
import java.util.List;

@Getter
public class SimplePageRequest {
    static final int MAX_SIZE = 100;

    @PositiveOrZero
    private int page;

    @Max(value = MAX_SIZE)
    @Min(value = 1)
    private int size;

    @Pattern(regexp="ASC|DESC")
    private String direction;

    private String[] orders;

    public Sort.Direction getDirection() {
            return  direction.equals("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

    public Pageable of(){
        if(direction == null || orders == null){
            return PageRequest.of(getPage(),getSize());
        }
        return  PageRequest.of(getPage(), getSize(), getDirection(), getOrders());
    }
}

package com.kog.mypage.novel.payload.request;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@Getter
public class NovelPageRequest {
    static final int MAX_SIZE = 50;
    static final int DEFAULT_SIZE = 10;

    private int page;
    private int size;
    private Sort.Direction direction;
    private String[] orders;

    public void setPage(int page) {
        this.page = page <=0 ? 1 : page;
    }

    public void setSize(int size) {
        this.size = size > 50 ? DEFAULT_SIZE : size;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }

    public void setOrders(String[] orders) {
        this.orders = orders;
    }

    public Pageable of(){
        if(direction == null || orders == null){
            return PageRequest.of(getPage(),getSize());
        }
        return  PageRequest.of(getPage(), getSize(), getDirection(), getOrders());
    }
}

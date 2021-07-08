package com.kog.mypage.novel.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("${service.api-gateway}")
public interface TicketClient {

    String URL_PREFIX = "/api/ticket";

    @GetMapping( value = "/",   produces = "application/json", consumes = "application/json")
    String get();
}

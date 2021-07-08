package com.kog.mypage.ticket.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("${service.api-gateway}")
public interface NovelClient {

    String URL_PREFIX = "/api/ticket";

    @GetMapping( value = "/",   produces = "application/json", consumes = "application/json")
    String get();
}

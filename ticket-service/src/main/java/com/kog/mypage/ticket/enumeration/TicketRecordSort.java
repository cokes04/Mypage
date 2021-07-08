package com.kog.mypage.ticket.enumeration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
@Getter
public enum TicketRecordSort {
    NEWEST(Sort.by(Sort.Direction.DESC, "createdDate")),
    OLDEST(Sort.by(Sort.Direction.ASC,"createdDate"));

    private final Sort sort;
}

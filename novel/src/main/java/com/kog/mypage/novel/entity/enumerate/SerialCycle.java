package com.kog.mypage.novel.entity.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SerialCycle {
    MONDAY("월"),TUESDAY("화"),WEDNESDAY("수"),THURSDAY("목"),FRIDAY("금"),SATURDAY("토"),SUNDAY("일");

    public final String value;

    public static SerialCycle toEnum(String value) {
        switch (value) {
            case "월":
                return SerialCycle.MONDAY;
            case "화":
                return SerialCycle.TUESDAY;
            case "수":
                return SerialCycle.WEDNESDAY;
            case "목":
                return SerialCycle.THURSDAY;
            case "금":
                return SerialCycle.FRIDAY;
            case "토":
                return SerialCycle.SATURDAY;
            case "일":
                return SerialCycle.SUNDAY;
            default :
                return null;
        }
    }

}

package com.kog.mypage.novel.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SerialCycle {
    MONDAY("월"),TUESDAY("화"),WEDNESDAY("수"),THURSDAY("목"),FRIDAY("금"),SATURDAY("토"),SUNDAY("일");

    public final String value;

    public static SerialCycle toEnum(String value) {
        for (SerialCycle serialCycle : values()) {
            if (serialCycle.getValue().equals(value))
                return serialCycle;
        }
        return null;
    }
}

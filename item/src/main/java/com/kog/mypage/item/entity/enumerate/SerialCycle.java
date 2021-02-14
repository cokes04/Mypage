package com.kog.mypage.item.entity.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SerialCycle {
    MONDAY("MO"),TUESDAY("TU"),WEDNESDAY("WE"),THURSDAY("TH"),FRIDAY("FR"),SATURDAY("SA"),SUNDAY("SU");

    private final String value;

    public static SerialCycle toEnum(String value) {
        switch (value) {
            case "MO":
                return SerialCycle.MONDAY;
            case "TU":
                return SerialCycle.TUESDAY;
            case "WE":
                return SerialCycle.WEDNESDAY;
            case "TH":
                return SerialCycle.THURSDAY;
            case "FR":
                return SerialCycle.FRIDAY;
            case "SA":
                return SerialCycle.SATURDAY;
            case "SU":
                return SerialCycle.SUNDAY;
            default :
                return null;
        }
    }

}

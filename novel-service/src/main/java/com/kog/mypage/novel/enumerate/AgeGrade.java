package com.kog.mypage.novel.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AgeGrade {
    ALL(-1), SEVEN(7), TWELVE(12), FIFTEEN(15), NINETEENL(19);

    public final int value;

    public static AgeGrade toEnum(int value) {
        for (AgeGrade ageGrade : values()) {
            if (ageGrade.getValue() == value )
                return ageGrade;
        }
        return null;
    }
}

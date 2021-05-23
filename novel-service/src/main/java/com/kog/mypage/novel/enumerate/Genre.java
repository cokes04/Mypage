package com.kog.mypage.novel.enumerate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Genre {
    FANTASY("fantasy"), ROMANCE("romance"), MARTIAL_ARTS("martial_arts");

    public final String value;

    public static Genre toEnum(String value) {
        for (Genre genre : values()) {
            if (genre.getValue().equals(value))
                return genre;
        }
        return null;
    }
}

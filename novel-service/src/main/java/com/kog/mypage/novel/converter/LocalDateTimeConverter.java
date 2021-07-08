package com.kog.mypage.novel.converter;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class LocalDateTimeConverter  implements AttributeConverter<LocalDateTime, Timestamp> {
    private LocalDateTime defaultTime = LocalDateTime.of(1000,1,1,1,1);

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {
        if (attribute == null) attribute = defaultTime;
        return Timestamp.valueOf(attribute);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp dbData) {
        return (dbData == null ? defaultTime : dbData.toLocalDateTime());
    }
}

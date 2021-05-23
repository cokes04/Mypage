package com.kog.mypage.novel.converter;

import com.kog.mypage.novel.enumerate.Genre;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.util.List;

@Component
public class GenreListToStringConverter implements AttributeConverter<List<Genre>, String> {
    private final String separator = ",";

    @Override
    public String convertToDatabaseColumn(List<Genre> attribute) {
        return EnumListToStringConverter
                .convertToDatabaseColumn(attribute, separator, (e) -> e.getValue());
    }

    @Override
    public List<Genre> convertToEntityAttribute(String dbData) {
        return EnumListToStringConverter
                .convertToEntityAttribute(dbData, separator, (data) -> Genre.toEnum(data));
    }
}

package com.kog.mypage.novel.converter;

import com.kog.mypage.novel.enumeration.SerialCycle;
import org.springframework.stereotype.Component;

import javax.persistence.AttributeConverter;
import java.util.List;

@Component
public class SerialCycleListToStringConverter implements AttributeConverter<List<SerialCycle>, String> {
    private final String separator = ",";

    @Override
    public String convertToDatabaseColumn(List<SerialCycle> attribute) {
        return EnumListToStringConverter
                .convertToDatabaseColumn(attribute, separator, (e) -> e.getValue());
    }

    @Override
    public List<SerialCycle> convertToEntityAttribute(String dbData) {
        return EnumListToStringConverter
                .convertToEntityAttribute(dbData, separator, (data) -> SerialCycle.toEnum(data));
    }
}

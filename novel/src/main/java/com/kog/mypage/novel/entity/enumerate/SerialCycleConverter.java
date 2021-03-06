package com.kog.mypage.novel.entity.enumerate;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SerialCycleConverter implements AttributeConverter<List<SerialCycle>, String> {
    @Override
    public String convertToDatabaseColumn(List<SerialCycle> attribute) {
        return attribute.stream()
                .sorted()
                .distinct()
                .map( cycle -> cycle.getValue())
                .collect(Collectors.joining(","));
    }

    @Override
    public List<SerialCycle> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(","))
                .map( data -> SerialCycle.toEnum(data))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

package com.kog.mypage.novel.converter;

import com.kog.mypage.novel.enumerate.Genre;

import javax.persistence.AttributeConverter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

abstract public class EnumListToStringConverter implements AttributeConverter<List<Genre>, String> {

    public static <T> String convertToDatabaseColumn(List<T> attribute, String separator,  Function<T, String> function) {
        return attribute.stream()
                .distinct()
                .sorted()
                .map(i -> function.apply(i) )
                .collect(Collectors.joining(separator));
    }

    public static <T> List<T> convertToEntityAttribute(String dbData, String separator, Function<String, T> function) {
        return Arrays.stream(dbData.split(separator))
                .distinct()
                .map( data ->  function.apply(data) )
                .sorted()
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

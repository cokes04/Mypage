package com.kog.mypage.novel.entity.converter;

import com.kog.mypage.novel.entity.enumerate.SerialCycle;

import javax.persistence.AttributeConverter;
import java.util.List;

public class BooleanToYNConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return attribute ? "Y" : "N";
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return dbData.equals("Y") ? true : false;
    }
}

package com.kog.mypage.novel.entity.enumerate;

import com.kog.mypage.novel.entity.converter.SerialCycleConverter;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


class SerialCycleConverterTest {

    SerialCycleConverter serialCycleConverter = new SerialCycleConverter();

    @Test
    void DailySerialCycleToDatabaseColumn() {
        List<SerialCycle> cycleList   = new ArrayList<SerialCycle>( Arrays.asList( SerialCycle.values() )  );

       String databaseColumn = serialCycleConverter.convertToDatabaseColumn(cycleList);
       String expected = "MO,TU,WE,TH,FR,SA,SU";

       assertEquals(7, cycleList.size());
       assertEquals(expected , databaseColumn);
    }

    @Test
    void puddleAndOverlapSerialCycleToDatabaseColumn() {
        List<SerialCycle> cycleList   = new ArrayList<SerialCycle>();
        cycleList.add(SerialCycle.SUNDAY); // 일
        cycleList.add(SerialCycle.SUNDAY); // 일
        cycleList.add(SerialCycle.SUNDAY); // 일
        cycleList.add(SerialCycle.SUNDAY); // 일

        cycleList.add(SerialCycle.WEDNESDAY); //수
        cycleList.add(SerialCycle.WEDNESDAY); //수

        cycleList.add(SerialCycle.TUESDAY); //화
        cycleList.add(SerialCycle.THURSDAY); // 목

        assertEquals(8, cycleList.size());

        String databaseColumn = serialCycleConverter.convertToDatabaseColumn(cycleList);
        String expected = "TU,WE,TH,SU";

        assertEquals(expected , databaseColumn);
    }

    @Test
    void ToEntityAttribute() {
        String databaseColumn = "TU,WE,TH,SU";

        List<SerialCycle> cyclesList   = serialCycleConverter.convertToEntityAttribute(databaseColumn);

        List<SerialCycle> expected   = new ArrayList<SerialCycle>();
        expected.add(SerialCycle.TUESDAY);
        expected.add(SerialCycle.WEDNESDAY);
        expected.add(SerialCycle.THURSDAY);
        expected.add(SerialCycle.SUNDAY);


        assertEquals(4, cyclesList.size());
        assertEquals(expected, cyclesList);

    }
}
package com.kog.mypage.novel.enumerate;

import com.kog.mypage.novel.converter.SerialCycleListToStringConverter;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


class SerialCycleConverterTest {

    SerialCycleListToStringConverter serialCycleConverter = new SerialCycleListToStringConverter();

    @Test
    void DailySerialCycleToDatabaseColumn() {
        List<SerialCycle> cycleList   = new ArrayList<SerialCycle>( Arrays.asList( SerialCycle.values() )  );

       String databaseColumn = serialCycleConverter.convertToDatabaseColumn(cycleList);
       String expected = "월,화,수,목,금,토,일";

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
        String expected = "화,수,목,일";

        assertEquals(expected , databaseColumn);
    }

    @Test
    void ToEntityAttribute() {
        String databaseColumn = "일,화,수,목";

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
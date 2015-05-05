package com.thoughtworks.tools.tsc.util;

import com.thoughtworks.tools.tsc.model.WorkingHours;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.*;

public class WorkingHoursUtilsTest {

    @Test
    public void testExtractWeeksWorksDayHours() throws Exception {

        List<Double> inRowValue = new ArrayList<>();
        inRowValue.add(8.0);
        inRowValue.add(8.0);
        inRowValue.add(8.0);
        inRowValue.add(8.0);
        inRowValue.add(8.0);
        inRowValue.add(8.0);
        inRowValue.add(6.0);
        Calendar startDay = Calendar.getInstance();
        startDay.set(Calendar.YEAR, 2015);
        startDay.set(Calendar.MONTH, 3);
        startDay.set(Calendar.DAY_OF_MONTH, 3);
        List<WorkingHours> workingHourses = WorkingHoursUtils.extractWeeksWorkingHours(startDay.getTime(), inRowValue);
        assertEquals(workingHourses.get(0).getWorkday(), startDay.getTime());
        startDay.set(Calendar.DAY_OF_MONTH, startDay.get(Calendar.DAY_OF_MONTH) + 6);
        assertTrue(workingHourses.get(workingHourses.size() - 1).getWorkHours() == 6);
        assertEquals(workingHourses.get(workingHourses.size() - 1).getWorkday(), startDay.getTime());
    }

}
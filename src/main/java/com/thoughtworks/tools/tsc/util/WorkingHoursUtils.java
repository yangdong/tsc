package com.thoughtworks.tools.tsc.util;

import com.thoughtworks.tools.tsc.model.WorkingHours;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public final class WorkingHoursUtils {


    public static List<WorkingHours> extractWeeksWorkingHours(Date startDate, List<Double> rowValue) {
        List<WorkingHours> result = new ArrayList<>();
        Calendar firstDay = Calendar.getInstance();
        firstDay.setTimeInMillis(startDate.getTime());
        for (int i = 0; i < 7; i++) {
            firstDay.set(Calendar.DAY_OF_MONTH, firstDay.get(Calendar.DAY_OF_MONTH) + (0 == i ? 0 : 1));
            WorkingHours workingHours = new WorkingHours(firstDay.getTime(), rowValue.get(i));
            result.add(workingHours);
        }
        return result;
    }
}

package com.thoughtworks.tools.tsc.core.handler.impl;

import com.thoughtworks.tools.tsc.config.DataDict;
import com.thoughtworks.tools.tsc.core.handler.AbstractTimeSheetHandler;
import com.thoughtworks.tools.tsc.exception.IllegalRowValueException;
import com.thoughtworks.tools.tsc.exception.SheetNotExistException;
import com.thoughtworks.tools.tsc.util.TWProjectMemberMgr;
import com.thoughtworks.tools.tsc.model.ExcelReadingReg;
import com.thoughtworks.tools.tsc.model.WorkingHours;
import com.thoughtworks.tools.tsc.util.WorkingHoursUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class TWTimeSheetHandler extends AbstractTimeSheetHandler {

    private static final String DEFAULT_SHEET_NAME = "Sheet1";

    public static final String NAME = "Resource: Full Name";
    public static final String BILLABLE = "Billable";
    public static final String START_DATE = "Start Date";
    public static final String MONDAY = "Monday Hours";
    public static final String TUESDAY = "Tuesday Hours";
    public static final String WEDNESDAY = "Wednesday Hours";
    public static final String THURSDAY = "Thursday Hours";
    public static final String FRIDAY = "Friday Hours";
    public static final String SATURDAY = "Saturday Hours";
    public static final String SUNDAY = "Sunday Hours";
    public static final String PROJECT_NAME = "Project Name";

    public TWTimeSheetHandler(ExcelReadingReg readingReg) throws SheetNotExistException {
        super(readingReg);
        readingReg.setReadColumns(PROJECT_NAME, NAME, START_DATE, BILLABLE, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY);
    }

    @Override
    protected boolean verifyProperties(List<Object> rowValue) {
        Object value = rowValue.get(1);
        if (value instanceof String) {
            return DataDict.Billable.BILLABLE_STR.equals(rowValue.get(1));
        } if (value instanceof Double) {
            return DataDict.Billable.BILLABLE_DOUBLE == (Double) value;
        }
        return super.verifyProperties(rowValue);
    }

    @Override
    protected void processRowData(List<Object> rowValue, Map<String, List<WorkingHours>> result) {
        String name = (String) rowValue.get(0);
        name = name.toLowerCase();
        TWProjectMemberMgr.addMemberToProject(name, (String) rowValue.get(10));
        List<WorkingHours> workingHourses = result.get(name);
        try {
            WorkingHours[] newWorkingHourses = extractWeekWorkingHours(rowValue);
            if (workingHourses == null) {
                result.put(name, new ArrayList<WorkingHours>());
                Collections.addAll(result.get(name),newWorkingHourses);
                return;
            }
            for (WorkingHours workingHours : newWorkingHourses) {
                if (workingHourses.contains(workingHours)) {
                    WorkingHours preWorkingHours = workingHourses.get(workingHourses.indexOf(workingHours));
                    preWorkingHours.setWorkHours(preWorkingHours.getWorkHours() + workingHours.getWorkHours());
                }else{
                    workingHourses.add(workingHours);
                }
            }
        } catch (IllegalRowValueException e) {
            // invalid row value,you needn't to handle it
        }
    }

    private WorkingHours[] extractWeekWorkingHours(List<Object> rowValue) throws IllegalRowValueException {
        Object date = rowValue.get(2);
        throwIllegalRowValueExceptionWhenNull(date);
        Date startDate = null;
        if (date instanceof Date) {
            startDate = (Date) date;
        } else if (date instanceof String) {
            try {
                startDate = new SimpleDateFormat("yyyy-MM-dd").parse((String) date);
            } catch (ParseException e) {
                throw new IllegalRowValueException("Make sure you date format as 2015-4-15", e);
            }
        }
        List<Double> weeksValue = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            throwIllegalRowValueExceptionWhenNull(rowValue.get(3 + i));
            weeksValue.add((double) rowValue.get(3 + i));
        }
        return WorkingHoursUtils.extractWeeksWorkingHours(startDate, weeksValue).toArray(new WorkingHours[0]);
    }

    private void throwIllegalRowValueExceptionWhenNull(Object value) throws IllegalRowValueException {
        if (null == value) {
            throw new IllegalRowValueException("Make sure you date format as 2015-4-15");
        }
    }
}

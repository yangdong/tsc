package com.thoughtworks.tools.tsc.core.handler.impl;

import com.thoughtworks.tools.tsc.config.DataDict;
import com.thoughtworks.tools.tsc.core.handler.AbstractTimeSheetHandler;
import com.thoughtworks.tools.tsc.util.TWProjectMemberMgr;
import com.thoughtworks.tools.tsc.model.ExcelReadingReg;
import com.thoughtworks.tools.tsc.model.WorkingHours;
import com.thoughtworks.tools.tsc.util.StringUtils;
import com.thoughtworks.tools.tsc.util.WorkingHoursUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class TWTimeSheetHandlerImpl extends AbstractTimeSheetHandler {

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

    public TWTimeSheetHandlerImpl(ExcelReadingReg readingReg) {
        super(readingReg);
        readingReg.setReadColumns(PROJECT_NAME, NAME, START_DATE, BILLABLE, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY);
    }

    @Override
    public Map<String, List<WorkingHours>> handle() throws IOException {
        TWProjectMemberMgr.clear();
        Map<String, List<WorkingHours>> result = new HashMap<>();
        Map<Integer, List<Object>> excelData = getExcelReader().readExcelAsMap(getExcelReadingRegs().getReadColumns());
        int index = 0;
        for (List<Object> rowValue : excelData.values()) {
            try {
                String name = (String) rowValue.get(0);
                if (index++ > 0 && !StringUtils.isEmpty(name) && DataDict.Billable.BILLABLE == (double) rowValue.get(1)) {
                    name = name.toLowerCase();
                    TWProjectMemberMgr.addMemberToProject(name, (String) rowValue.get(10));
                    if (result.get(name) == null) {
                        result.put(name, new ArrayList<WorkingHours>());
                    }
                    Collections.addAll(result.get(name), extractWeekWorkingHours(rowValue));
                }
            } catch (Exception e) {
                //do nothing...
            }
        }
        return result;
    }

    private WorkingHours[] extractWeekWorkingHours(List<Object> rowValue) {
        Date startDate = null;
        try {
            startDate = (Date) rowValue.get(2);
        } catch (Exception e) {
            try {
                startDate = new SimpleDateFormat("yyyy-MM-dd").parse((String) rowValue.get(2));
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        List<Double> weeksValue = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            weeksValue.add((double) rowValue.get(3 + i));
        }
        return WorkingHoursUtils.extractWeeksWorkingHours(startDate, weeksValue).toArray(new WorkingHours[0]);
    }
}

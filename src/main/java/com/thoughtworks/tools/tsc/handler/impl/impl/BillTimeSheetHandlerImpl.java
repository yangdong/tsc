package com.thoughtworks.tools.tsc.handler.impl.impl;

import com.thoughtworks.tools.tsc.config.DataDict;
import com.thoughtworks.tools.tsc.handler.impl.AbstractTimeSheetHandler;
import com.thoughtworks.tools.tsc.model.ExcelReadingReg;
import com.thoughtworks.tools.tsc.model.WorkingHours;

import java.io.IOException;
import java.util.*;

public class BillTimeSheetHandlerImpl extends AbstractTimeSheetHandler {

    public static final String NAME = "Name";
    public static final String DATE = "Date";
    public static final String NUMBER = "Number";
    public static final String WBS = "WBS Elem Desc";

    public BillTimeSheetHandlerImpl(ExcelReadingReg readingReg) {
        super(readingReg);
        readingReg.setReadColumns(NAME, DATE, NUMBER, WBS);
    }

    @Override
    public Map<String, List<WorkingHours>> handle() throws IOException {

        Map<String, List<WorkingHours>> result = new HashMap<>();
        Map<Integer, List<Object>> excelData = getExcelReader().readExcelAsMap(getExcelReadingRegs().getReadColumns());
        int index = 0;
        for (List<Object> rowValue : excelData.values()) {
            if (index++ > 0 && !DataDict.WBSElemDesc.UNBILLABEL_DESC.equals(rowValue.get(3))) {
                String name = ((String) rowValue.get(0)).toLowerCase();
                WorkingHours workingHours = new WorkingHours((Date) rowValue.get(1), (Double) rowValue.get(2));
                List<WorkingHours> workingHourses = result.get(name);
                if (workingHourses == null) {
                    result.put(name, new ArrayList<WorkingHours>());
                    result.get(name).add(workingHours);
                } else if (workingHourses.contains(workingHours)) {
                    WorkingHours currentWorkingHours = workingHourses.get(workingHourses.indexOf(workingHours));
                    currentWorkingHours.setWorkHours(currentWorkingHours.getWorkHours() + workingHours.getWorkHours());
                } else {
                    result.get(name).add(workingHours);
                }
            }
        }
        return result;
    }
}

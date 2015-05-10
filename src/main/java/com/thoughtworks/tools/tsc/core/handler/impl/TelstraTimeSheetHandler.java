package com.thoughtworks.tools.tsc.core.handler.impl;

import com.thoughtworks.tools.tsc.config.DataDict;
import com.thoughtworks.tools.tsc.core.ExcelParser;
import com.thoughtworks.tools.tsc.core.handler.AbstractTimeSheetHandler;
import com.thoughtworks.tools.tsc.exception.SheetNotExistException;
import com.thoughtworks.tools.tsc.model.ExcelReadingReg;
import com.thoughtworks.tools.tsc.model.WorkingHours;

import java.io.IOException;
import java.util.*;

public class TelstraTimeSheetHandler extends AbstractTimeSheetHandler {

    public static final String NAME = "Name";
    public static final String DATE = "Date";
    public static final String NUMBER = "Number";
    public static final String WBS = "WBS Elem Desc";

    public TelstraTimeSheetHandler(ExcelReadingReg readingReg) throws SheetNotExistException {
        super(readingReg);
        readingReg.setReadColumns(NAME, DATE, NUMBER, WBS);
    }

    @Override
    protected void processRowData(List<Object> rowValue, Map<String, List<WorkingHours>> result) {

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

    @Override
    protected boolean verifyProperties(List<Object> rowValue) {
        return !DataDict.WBSElemDesc.UNBILLABEL_DESC.equals(rowValue.get(3));
    }

    @Override
    public String getDefaultSheetName() {
        return "PEX ";
    }
}

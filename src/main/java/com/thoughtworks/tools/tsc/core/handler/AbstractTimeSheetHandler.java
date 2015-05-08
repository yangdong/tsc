package com.thoughtworks.tools.tsc.core.handler;

import com.thoughtworks.tools.tsc.core.ExcelParser;
import com.thoughtworks.tools.tsc.exception.SheetNotExistException;
import com.thoughtworks.tools.tsc.model.ExcelReadingReg;
import com.thoughtworks.tools.tsc.model.WorkingHours;
import com.thoughtworks.tools.tsc.util.StringUtils;
import com.thoughtworks.tools.tsc.util.TWProjectMemberMgr;

import java.io.File;
import java.io.IOException;
import java.util.*;

public abstract class AbstractTimeSheetHandler {

    private static final String DEFAULT_SHEET_NAME = "Sheet1";

    private ExcelReadingReg excelReadingRegs;
    private ExcelParser excelParser;

    public AbstractTimeSheetHandler(ExcelReadingReg readingReg) throws SheetNotExistException {
        setReadingReg(readingReg);
        excelParser = new ExcelParser(readingReg.getFile());
    }

    public void setReadingReg(ExcelReadingReg readingReg) {
        if (!new File(readingReg.getFile()).exists()) {
            throw new IllegalArgumentException("File \"" + readingReg.getFile() + "\" is not exists!");
        }
        this.excelReadingRegs = readingReg;
    }

    public ExcelParser getExcelParser() {
        return excelParser;
    }

    public ExcelReadingReg getExcelReadingRegs() {
        return excelReadingRegs;
    }

    public Map<String, List<WorkingHours>> handle() throws IOException {
        TWProjectMemberMgr.clear();
        Map<String, List<WorkingHours>> result = new HashMap<>();
        Map<Integer, List<Object>> excelData = getExcelParser().readExcelAsMap(getExcelReadingRegs().getReadColumns());
        for (List<Object> rowValue : excelData.values()) {
            if (null != rowValue.get(0) && checkProperties(rowValue)) {
                processRowData(rowValue, result);
            }
        }
        return result;
    }

    protected abstract void processRowData(List<Object> rowValue, Map<String, List<WorkingHours>> result);

    protected boolean checkProperties(List<Object> rowValue) {
        return true;
    }

    public String getDefaultSheetName() {
        return DEFAULT_SHEET_NAME;
    }
}

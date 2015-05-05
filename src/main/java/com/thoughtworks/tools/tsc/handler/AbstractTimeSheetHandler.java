package com.thoughtworks.tools.tsc.handler;

import com.thoughtworks.tools.tsc.core.ExcelReader;
import com.thoughtworks.tools.tsc.model.ExcelReadingReg;
import com.thoughtworks.tools.tsc.model.WorkingHours;

import java.io.File;
import java.io.IOException;
import java.util.*;

public abstract class AbstractTimeSheetHandler {

    private ExcelReadingReg excelReadingRegs;
    private ExcelReader excelReader;

    public AbstractTimeSheetHandler(ExcelReadingReg readingReg){
        setReadingReg(readingReg);
        excelReader = new ExcelReader(readingReg.getFile());
    }

    public void setReadingReg(ExcelReadingReg readingReg) {
        if (!new File(readingReg.getFile()).exists()) {
            throw new IllegalArgumentException("File \"" + readingReg.getFile() + "\" is not exists!");
        }
        this.excelReadingRegs  = readingReg;
    }

    public ExcelReader getExcelReader(){
        return excelReader;
    }

    public ExcelReadingReg getExcelReadingRegs(){
        return excelReadingRegs;
    }

    public abstract Map<String,List<WorkingHours>> handle ()throws IOException;
}

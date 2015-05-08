package com.thoughtworks.tools.tsc.core;

import com.thoughtworks.tools.tsc.core.handler.impl.TelstraTimeSheetHandler;
import com.thoughtworks.tools.tsc.core.handler.impl.TWTimeSheetHandler;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ExcelReaderTest {


    @Test
    public void testReadTWExcel() throws Exception {
        ExcelParser excelReader = new ExcelParser("/Users/sjyuan/Personal-sjyuan/Works/Documents/TimeSheetReports/April/TW_ts_April.xlsx");
        excelReader.readExcelAsMap(TWTimeSheetHandler.PROJECT_NAME, TWTimeSheetHandler.NAME, TWTimeSheetHandler.BILLABLE, TWTimeSheetHandler.START_DATE, TWTimeSheetHandler.MONDAY, TWTimeSheetHandler.TUESDAY, TWTimeSheetHandler.WEDNESDAY, TWTimeSheetHandler.THURSDAY, TWTimeSheetHandler.FRIDAY);
        List<String> sheetColumns = excelReader.getSheetColumns();
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandler.PROJECT_NAME));
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandler.NAME));
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandler.BILLABLE));
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandler.START_DATE));
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandler.MONDAY));
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandler.TUESDAY));
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandler.WEDNESDAY));
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandler.THURSDAY));
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandler.FRIDAY));
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandler.SATURDAY));
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandler.SUNDAY));
    }

    @Test
    public void testReadBillExcel() throws Exception {
        ExcelParser excelReader = new ExcelParser("/Users/sjyuan/Personal-sjyuan/Works/Documents/TimeSheetReports/April/Telstra_ts_April.xlsx");
        excelReader.readExcelAsMap(TelstraTimeSheetHandler.NAME, TelstraTimeSheetHandler.DATE, TelstraTimeSheetHandler.NUMBER, TelstraTimeSheetHandler.WBS);
        List<String> sheetColumns = excelReader.getSheetColumns();
        assertTrue(-1 != sheetColumns.indexOf(TelstraTimeSheetHandler.NAME));
        assertTrue(-1 != sheetColumns.indexOf(TelstraTimeSheetHandler.DATE));
        assertTrue(-1 != sheetColumns.indexOf(TelstraTimeSheetHandler.NUMBER));
        assertTrue(-1 != sheetColumns.indexOf(TelstraTimeSheetHandler.WBS));
    }
}
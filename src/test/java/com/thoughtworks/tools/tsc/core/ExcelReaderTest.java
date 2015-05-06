package com.thoughtworks.tools.tsc.core;

import com.thoughtworks.tools.tsc.core.handler.impl.BillTimeSheetHandlerImpl;
import com.thoughtworks.tools.tsc.core.handler.impl.TWTimeSheetHandlerImpl;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ExcelReaderTest {


    @Test
    public void testReadTWExcel() throws Exception {
        ExcelParser excelReader = new ExcelParser("/Users/sjyuan/Personal-sjyuan/Works/Documents/TimeSheetReports/April/TW_ts_April.xlsx");
        excelReader.readExcelAsMap(TWTimeSheetHandlerImpl.PROJECT_NAME,TWTimeSheetHandlerImpl.NAME, TWTimeSheetHandlerImpl.BILLABLE, TWTimeSheetHandlerImpl.START_DATE, TWTimeSheetHandlerImpl.MONDAY, TWTimeSheetHandlerImpl.TUESDAY, TWTimeSheetHandlerImpl.WEDNESDAY, TWTimeSheetHandlerImpl.THURSDAY, TWTimeSheetHandlerImpl.FRIDAY);
        List<String> sheetColumns = excelReader.getSheetColumns();
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandlerImpl.PROJECT_NAME));
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandlerImpl.NAME));
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandlerImpl.BILLABLE));
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandlerImpl.START_DATE));
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandlerImpl.MONDAY));
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandlerImpl.TUESDAY));
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandlerImpl.WEDNESDAY));
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandlerImpl.THURSDAY));
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandlerImpl.FRIDAY));
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandlerImpl.SATURDAY));
        assertTrue(-1 != sheetColumns.indexOf(TWTimeSheetHandlerImpl.SUNDAY));
    }

    @Test
    public void testReadBillExcel() throws Exception {
        ExcelParser excelReader = new ExcelParser("/Users/sjyuan/Personal-sjyuan/Works/Documents/TimeSheetReports/April/Telstra_ts_April.xlsx");
        excelReader.readExcelAsMap(BillTimeSheetHandlerImpl.NAME, BillTimeSheetHandlerImpl.DATE, BillTimeSheetHandlerImpl.NUMBER, BillTimeSheetHandlerImpl.WBS);
        List<String> sheetColumns = excelReader.getSheetColumns();
        assertTrue(-1 != sheetColumns.indexOf(BillTimeSheetHandlerImpl.NAME));
        assertTrue(-1 != sheetColumns.indexOf(BillTimeSheetHandlerImpl.DATE));
        assertTrue(-1 != sheetColumns.indexOf(BillTimeSheetHandlerImpl.NUMBER));
        assertTrue(-1 != sheetColumns.indexOf(BillTimeSheetHandlerImpl.WBS));
    }
}
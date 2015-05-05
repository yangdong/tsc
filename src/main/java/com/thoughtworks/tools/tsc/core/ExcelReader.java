package com.thoughtworks.tools.tsc.core;


import com.thoughtworks.tools.tsc.util.ExcelUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * A reader to read excel file,default read the sheet of index 0
 */
public class ExcelReader {

    private static final int DEFAULT_SHEET_INDEX = 0;

    private final List<String> sheetColumns = new ArrayList<>();

    private final XSSFSheet sheet;


    public ExcelReader(String excelFilePath) {
        this(excelFilePath, DEFAULT_SHEET_INDEX);
    }

    /**
     * Create a instance of excel reader,assign the selected sheet to be read
     *
     * @param excelFilePath      excel File path
     * @param selectedSheetIndex selected sheet index,arrange from 0~...
     */
    public ExcelReader(String excelFilePath, int selectedSheetIndex) {
        XSSFWorkbook workBook;
        try {
            workBook = new XSSFWorkbook(new FileInputStream(excelFilePath));
        } catch (IOException e) {
            throw new IllegalArgumentException("Execl file path is invalid!", e);
        }
        sheet = workBook.getSheetAt(selectedSheetIndex);
    }


    public Map<Integer, List<Object>> readExcelAsMap(String... selectedColumns) throws IOException {
        Map<Integer, List<Object>> result = new LinkedHashMap<>();
        Iterator<Row> rowIterator = sheet.iterator();
        int rowNum = 0;
        int[] selectedIndexs = null;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (0 == rowNum) {
                selectedIndexs = calculateSelectedIndexs(row, selectedColumns);
            }
            result.put(rowNum++, extractRowValue(row, selectedIndexs));
        }
        return result;
    }

    /**
     * calculate the indexs of select columns,also do the task of extracting all the columns of the sheet</br>
     * this method will be invoked once when titleRow number is zero
     *
     * @param titleRow        title row of the sheet
     * @param selectedColumns select columns
     * @return the indexs of the select columns in the sheet
     */
    private int[] calculateSelectedIndexs(Row titleRow, String... selectedColumns) {
        Iterator<Cell> cellIterator = titleRow.cellIterator();
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            sheetColumns.add(cell.getStringCellValue());
        }
        return ExcelUtils.calculateSelectedIndexs(sheetColumns, selectedColumns);
    }

    private List<Object> extractRowValue(Row row, int[] selectedIndexArray) {
        List<Object> rowValueList = new ArrayList<>();
        if (null != selectedIndexArray) {
            for (int index : selectedIndexArray) {
                rowValueList.add(extractCellValue(row.getCell(index)));
            }
        } else {
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                rowValueList.add(extractCellValue(cellIterator.next()));
            }
        }
        return rowValueList;
    }

    private Object extractCellValue(Cell cell) {
        if (null != cell) {
            if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                return cell.getNumericCellValue() <= 8 ? cell.getNumericCellValue() : cell.getDateCellValue();
            } else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                return cell.getStringCellValue();
            }
        }
        return null;
    }

    public List<String> getSheetColumns() {
        return sheetColumns;
    }
}
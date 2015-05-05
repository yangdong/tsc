package com.thoughtworks.tools.tsc.util;

import java.util.Arrays;
import java.util.List;

public final class ExcelUtils {



    public static int[] calculateSelectedIndexs(List<String> columns, String... selectedColumns) {
        if (null == selectedColumns || 0 == selectedColumns.length) {
            return null;
        }
        int[] result = new int[selectedColumns.length];
        int index = 0;
        int col = 0;
        List<String> selectedColumnList = Arrays.asList(selectedColumns);
        for (String column : columns) {
            if (selectedColumnList.contains(column)) {
                result[index++] = col;
            }
            col++;
        }
        return result;
    }
}

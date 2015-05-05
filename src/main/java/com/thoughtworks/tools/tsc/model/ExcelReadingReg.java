package com.thoughtworks.tools.tsc.model;

import java.io.Serializable;
import java.util.Arrays;

public class ExcelReadingReg implements Serializable{

    private String file;
    private String[] readColumns;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String[] getReadColumns() {
        return readColumns;
    }

    public void setReadColumns(String... readColumns) {
        this.readColumns = readColumns;
    }

    @Override
    public String toString() {
        return "ExcelReadingReg{" +
                "file='" + file + '\'' +
                ", readColumns=" + Arrays.toString(readColumns) +
                '}';
    }
}

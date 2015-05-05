package com.thoughtworks.tools.tsc.main;

import com.thoughtworks.tools.tsc.core.Matcher;
import com.thoughtworks.tools.tsc.model.ExcelReadingReg;
import com.thoughtworks.tools.tsc.out.impl.ConsoleLogImpl;
import com.thoughtworks.tools.tsc.out.impl.FileLogImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {

    public static void main(String[] args) {

        ExcelReadingReg twReadingReg = new ExcelReadingReg();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            System.out.println("Please input the Timesheet file path of ThougthWorks:");
            String twFilePath = br.readLine();
            if (null == twFilePath || "".equals(twFilePath.trim())) {
//                twFilePath = "/Users/sjyuan/Personal-sjyuan/Works/Documents/TimeSheetReports/March/TW_ts_March.xlsx";
                twFilePath = "/Users/sjyuan/Personal-sjyuan/Works/Documents/TimeSheetReports/April/TW_ts_April.xlsx";
                twReadingReg.setFile(twFilePath);
            }
            System.out.println("Please input the Timesheet file path of Telstra:");
            String billFilePath = br.readLine();
            ExcelReadingReg billReadingReg = new ExcelReadingReg();
            if (null == billFilePath || "".equals(billFilePath.trim())) {
                billFilePath = "/Users/sjyuan/Personal-sjyuan/Works/Documents//TimeSheetReports/March/export.xlsx";
//                billFilePath = "/Users/sjyuan/Personal-sjyuan/Works/Documents/TimeSheetReports/April/Telstra_ts_April.xlsx";
            }
            billReadingReg.setFile(billFilePath);
            br.close();

            Matcher matcher = new Matcher(new ConsoleLogImpl());
            matcher.matchByProject(billReadingReg, twReadingReg);
            matcher.output();

            Matcher matcher2 = new Matcher(new FileLogImpl("/Users/sjyuan/TSR.txt"));
            matcher2.matchByProject(billReadingReg, twReadingReg);
            matcher2.output();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

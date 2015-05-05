package com.thoughtworks.tools.tsc.util;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ExcelUtilsTest {

    @Test
    public void testCalculateSelectedIndexs() throws Exception {
        List<String> columns = new ArrayList<>();
        Collections.addAll(columns, "Name", "Start Date", "Number");
        int[] indexs = ExcelUtils.calculateSelectedIndexs(columns, "Name","Number");
        assertTrue(2 == indexs.length);
        assertTrue(2 == indexs[1]);
    }
}
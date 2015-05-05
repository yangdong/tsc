package com.thoughtworks.tools.tsc.util;


import java.io.Serializable;
import java.util.Comparator;

public final class StringUtils{

    public static String upperCaseOfFirstChar(String str) {
        if (null == str && "".equals(str.trim())) {
            return str;
        }
        String first = str.substring(0, 1);
        return first.toUpperCase() + str.substring(1, str.length());
    }

    public static boolean isEmpty(String input){
        return null == input || "".equals(input.trim());
    }
}

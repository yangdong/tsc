package com.thoughtworks.tools.tsc.util;


import com.thoughtworks.tools.tsc.model.MismatchProperties;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;

public final class StringUtils{

    public static String upperCaseOfFirstChar(String str) {
        if (null == str && "".equals(str.trim())) {
            return str;
        }
        String first = str.substring(0, 1);
        return first.toUpperCase() + str.substring(1, str.length());
    }

    public static String generateMsg(Map<String, Map<String, Set<MismatchProperties>>> data){
        StringBuilder result = new StringBuilder();
        for (Map.Entry<String, Map<String, Set<MismatchProperties>>> value : data.entrySet()) {
            for (Map.Entry<String, Set<MismatchProperties>> entry : value.getValue().entrySet()) {
                result.append("-----"+entry.getKey() + " [project:" + value.getKey() + "]");
                for (MismatchProperties properties : entry.getValue()) {
                    result.append("\n");
                    result.append(properties.toString());
                }
                result.append("\n\n");
            }
        }
        return result.toString();
    }

    public static boolean isEmpty(String input){
        return null == input || "".equals(input.trim());
    }
}

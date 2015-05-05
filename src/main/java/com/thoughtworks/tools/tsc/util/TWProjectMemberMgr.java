package com.thoughtworks.tools.tsc.util;

import javax.lang.model.element.Name;
import java.util.HashMap;
import java.util.Map;

public final class TWProjectMemberMgr {

    private static final Map<String, String> NAME_PROJECT = new HashMap<>();

    public static void addMemberToProject(String projectName, String memberName) {
        NAME_PROJECT.put(projectName, memberName);
    }
    public static void clear(){
        NAME_PROJECT.clear();
    }

    public static String queryProjectByMember(String memberName) {
        memberName = memberName.toLowerCase();
        String result = NAME_PROJECT.get(memberName);
        if (null == result) {
            String[] firstAndLastNames = memberName.split(" +");
            memberName = firstAndLastNames[1] + " " + firstAndLastNames[0];
            result = NAME_PROJECT.get(memberName);
        }
        return result;
    }
}

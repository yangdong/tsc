package com.thoughtworks.tools.tsc.core;

import com.thoughtworks.tools.tsc.util.TWProjectMemberMgr;
import com.thoughtworks.tools.tsc.handler.impl.BillTimeSheetHandlerImpl;
import com.thoughtworks.tools.tsc.handler.impl.TWTimeSheetHandlerImpl;
import com.thoughtworks.tools.tsc.model.ExcelReadingReg;
import com.thoughtworks.tools.tsc.model.MismatchProperties;
import com.thoughtworks.tools.tsc.model.WorkingHours;
import com.thoughtworks.tools.tsc.util.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

/**
 * Matcher do the job of matching two data of Timesheet. </br>
 */
@Component
public class Matcher {

    private List<Map<String, Set<MismatchProperties>>> matchResult;
    private Map<String, Map<String, Set<MismatchProperties>>> matchResultGroupByProject;


    public Map<String, Map<String, Set<MismatchProperties>>> matchByProject(ExcelReadingReg billReadingReg, ExcelReadingReg twReadingReg) throws IOException {
        matchResultGroupByProject = new HashMap<>();
        List<Map<String, Set<MismatchProperties>>> matchData = match(billReadingReg, twReadingReg);
        for (Map<String, Set<MismatchProperties>> value : matchData) {
            for (Map.Entry<String, Set<MismatchProperties>> nameMismatch : value.entrySet()) {
                String projectName = TWProjectMemberMgr.queryProjectByMember(nameMismatch.getKey());
                if (null == matchResultGroupByProject.get(projectName)) {
                    matchResultGroupByProject.put(projectName, value);
                } else {
                    matchResultGroupByProject.get(projectName).put(nameMismatch.getKey(), nameMismatch.getValue());
                }

            }
        }
        return matchResultGroupByProject;
    }

    private List<Map<String, Set<MismatchProperties>>> match(ExcelReadingReg billReadingReg, ExcelReadingReg twReadingReg) throws IOException {
        BillTimeSheetHandlerImpl billResultHandler = new BillTimeSheetHandlerImpl(billReadingReg);
        Map<String, List<WorkingHours>> billResult = billResultHandler.handle();
        TWTimeSheetHandlerImpl twResultHandler = new TWTimeSheetHandlerImpl(twReadingReg);
        Map<String, List<WorkingHours>> twResult = twResultHandler.handle();
        matchResult = matchName(twResult, billResult);
        return matchResult;
    }


    private List<Map<String, Set<MismatchProperties>>> matchName(Map<String, List<WorkingHours>> twResult, Map<String, List<WorkingHours>> billResult) {
        List<Map<String, Set<MismatchProperties>>> result = new ArrayList<>();
        for (Map.Entry<String, List<WorkingHours>> entry : billResult.entrySet()) {
            String name = entry.getKey();
            List<WorkingHours> twWorkingSet = extractWorkingHoursAsList(name, twResult);
            if (twWorkingSet != null) {
                List<WorkingHours> billWorkingSet = entry.getValue();
                Map<String, Set<MismatchProperties>> nameDismatchSet = matchWorkingHours(name, twWorkingSet, billWorkingSet);
                if (nameDismatchSet.size() > 0) {
                    result.add(nameDismatchSet);
                }
            }
        }
        return result;
    }

    private Map<String, Set<MismatchProperties>> matchWorkingHours(String name, List<WorkingHours> twWorkingHoursList, List<WorkingHours> billWorkingHoursList) {
        Map<String, Set<MismatchProperties>> result = new HashMap<>();
        Set<MismatchProperties> mismatchProperties = new TreeSet<>();
        for (WorkingHours billWorkingHours : billWorkingHoursList) {
            if (twWorkingHoursList.contains(billWorkingHours)) {
                WorkingHours twWorkingHours = twWorkingHoursList.get(twWorkingHoursList.indexOf(billWorkingHours));
                if (twWorkingHours.getWorkHours() != billWorkingHours.getWorkHours()) {
                    MismatchProperties properties = new MismatchProperties(billWorkingHours.getWorkday(), twWorkingHours.getWorkHours(), billWorkingHours.getWorkHours());
                    mismatchProperties.add(properties);
                    result.put(convertWithNameStyle(name), mismatchProperties);
                }
            }
        }
        return result;
    }

    private List<WorkingHours> extractWorkingHoursAsList(String name, Map<String, List<WorkingHours>> nameWorkingMap) {
        List<WorkingHours> result = nameWorkingMap.get(name);
        if (null == result) {
            String[] names = name.split(" +");
            result = nameWorkingMap.get(names[1] + " " + names[0]);
        }
        return result;
    }

    private String convertWithNameStyle(String preName) {
        if (null == preName && "".equals(preName.trim())) {
            return preName;
        }
        String[] names = preName.split(" +");
        return StringUtils.upperCaseOfFirstChar(names[0]) + " " + StringUtils.upperCaseOfFirstChar(names[1]);
    }
}



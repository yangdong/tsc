package com.thoughtworks.tools.tsc.core;

import com.thoughtworks.tools.tsc.exception.SheetNotExistException;
import com.thoughtworks.tools.tsc.util.TWProjectMemberMgr;
import com.thoughtworks.tools.tsc.core.handler.impl.TelstraTimeSheetHandler;
import com.thoughtworks.tools.tsc.core.handler.impl.TWTimeSheetHandler;
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


    public Map<String, Map<String, Set<MismatchProperties>>> matchByProject(ExcelReadingReg telstraReadingReg, ExcelReadingReg twReadingReg) throws IOException, SheetNotExistException {
        matchResultGroupByProject = new HashMap<>();
        List<Map<String, Set<MismatchProperties>>> matchData = match(telstraReadingReg, twReadingReg);
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

    private List<Map<String, Set<MismatchProperties>>> match(ExcelReadingReg telstraReadingReg, ExcelReadingReg twReadingReg) throws IOException, SheetNotExistException {
        TelstraTimeSheetHandler telstraResultHandler = new TelstraTimeSheetHandler(telstraReadingReg);
        TWTimeSheetHandler twResultHandler = new TWTimeSheetHandler(twReadingReg);
        Map<String, List<WorkingHours>> telstraResult = telstraResultHandler.handle();
        Map<String, List<WorkingHours>> twResult = twResultHandler.handle();
        matchResult = matchName(twResult, telstraResult);
        return matchResult;
    }


    private List<Map<String, Set<MismatchProperties>>> matchName(Map<String, List<WorkingHours>> twResult, Map<String, List<WorkingHours>> telstraResult) {
        List<Map<String, Set<MismatchProperties>>> result = new ArrayList<>();
        for (Map.Entry<String, List<WorkingHours>> entry : telstraResult.entrySet()) {
            String name = entry.getKey();
            List<WorkingHours> twWorkingSet = extractWorkingHoursAsList(name, twResult);
            if (twWorkingSet != null) {
                List<WorkingHours> telstraWorkingSet = entry.getValue();
                Map<String, Set<MismatchProperties>> nameDismatchSet = matchWorkingHours(name, twWorkingSet, telstraWorkingSet);
                if (nameDismatchSet.size() > 0) {
                    result.add(nameDismatchSet);
                }
            }
        }
        return result;
    }


    private Map<String, Set<MismatchProperties>> matchWorkingHours(String name,
                                                                   List<WorkingHours> twWorkingHoursList,
                                                                   List<WorkingHours> telstraWorkingHoursList) {
        Map<String, Set<MismatchProperties>> result = new HashMap<>();
        Set<MismatchProperties> mismatchProperties = new TreeSet<>();
        for (WorkingHours twWorkingHours : twWorkingHoursList) {
            if (telstraWorkingHoursList.contains(twWorkingHours)) {
                WorkingHours telstraWorkingHours = telstraWorkingHoursList.get(telstraWorkingHoursList.indexOf(twWorkingHours));
                if (telstraWorkingHours.getWorkHours() != twWorkingHours.getWorkHours()) {
                    MismatchProperties properties = new MismatchProperties(twWorkingHours.getWorkday(), twWorkingHours.getWorkHours(), telstraWorkingHours.getWorkHours());
                    mismatchProperties.add(properties);
                }
            } else if(0 != twWorkingHours.getWorkHours()){
                MismatchProperties properties = new MismatchProperties(twWorkingHours.getWorkday(), twWorkingHours.getWorkHours(), -1);
                mismatchProperties.add(properties);
            }
        }
        if (mismatchProperties.size() > 0) {
            result.put(convertWithNameStyle(name), mismatchProperties);
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


    private String convertWithNameStyle(String name) {
        if (StringUtils.isEmpty(name)) {
            return name;
        }
        String[] names = name.split(" +");
        return StringUtils.upperCaseOfFirstChar(names[0]) + " " + StringUtils.upperCaseOfFirstChar(names[1]);
    }
}
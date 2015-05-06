package com.thoughtworks.tools.tsc.service.impl;

import com.thoughtworks.tools.tsc.core.Matcher;
import com.thoughtworks.tools.tsc.model.ExcelReadingReg;
import com.thoughtworks.tools.tsc.model.MismatchProperties;
import com.thoughtworks.tools.tsc.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Service("timesheetService")
public class TimesheetServiceImpl implements TimesheetService {

    @Autowired
    private Matcher matcher;

    @Override
    public Map<String, Map<String, Set<MismatchProperties>>> matchTimesheetService(String twTimesheetFilePath, String telstraTimesheetFilePath) throws IOException {
        ExcelReadingReg twReadingReg = new ExcelReadingReg();
        twReadingReg.setFile(twTimesheetFilePath);
        ExcelReadingReg telstraReadingReg = new ExcelReadingReg();
        telstraReadingReg.setFile(telstraTimesheetFilePath);
        return matcher.matchByProject(telstraReadingReg,twReadingReg);
    }
}

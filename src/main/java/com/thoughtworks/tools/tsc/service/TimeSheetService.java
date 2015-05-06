package com.thoughtworks.tools.tsc.service;

import com.thoughtworks.tools.tsc.model.MismatchProperties;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public interface TimesheetService {

    Map<String, Map<String, Set<MismatchProperties>>> matchTimesheetService(String twTimesheetFilePath,String telstraTimesheetFilePath) throws IOException;
}

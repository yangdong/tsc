package com.thoughtworks.tools.tsc.web.controller;

import com.thoughtworks.tools.tsc.exception.SheetNotExistException;
import com.thoughtworks.tools.tsc.model.MismatchProperties;
import com.thoughtworks.tools.tsc.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.yood.commons.util.io.FileUpDownUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Controller
public class TimeSheetUploadController {

    @Autowired
    private TimesheetService timesheetService;


    @RequestMapping(value = "match",
                    method = RequestMethod.POST)
    public ModelAndView matching(@RequestParam("twFilePath") MultipartFile twFile, @RequestParam("telstraFilePath") MultipartFile telstraFile, HttpServletRequest request) throws IOException {

        ModelAndView result = new ModelAndView("result");

        String savaPathOfTwFile = FileUpDownUtils.saveFileToServer(twFile, request, null);
        String savaPathOfTelstraFile = FileUpDownUtils.saveFileToServer(telstraFile, request, null);

        Map<String, Map<String, Set<MismatchProperties>>> matchResult = null;
        try {
            matchResult = timesheetService.matchTimesheetService(savaPathOfTwFile, savaPathOfTelstraFile);
        } catch (SheetNotExistException e) {
            e.printStackTrace();
        }
        result.addObject("result",matchResult);
        return result;
    }
}

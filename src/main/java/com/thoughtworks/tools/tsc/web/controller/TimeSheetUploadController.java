package com.thoughtworks.tools.tsc.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.tools.tsc.core.Matcher;
import com.thoughtworks.tools.tsc.service.TimesheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.xml.ws.RequestWrapper;

@Controller
public class TimeSheetUploadController {

    @Autowired
    private TimesheetService timesheetService;


    @RequestMapping(value = "",method = RequestMethod.GET)
    public JSONObject matching(){


        return null;
    }
}

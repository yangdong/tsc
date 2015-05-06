package com.thoughtworks.tools.tsc.out.impl;

import com.thoughtworks.tools.tsc.model.MismatchProperties;
import com.thoughtworks.tools.tsc.out.IOuter;
import com.thoughtworks.tools.tsc.util.StringUtils;

import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class ConsoleLogImpl implements IOuter {

    @Override
    public void output(Map<String,Map<String, Set<MismatchProperties>>> result) {
        Logger.getGlobal().info("\n"+StringUtils.generateMsg(result));
    }
}

package com.thoughtworks.tools.tsc.out.impl;

import com.thoughtworks.tools.tsc.model.MismatchProperties;
import com.thoughtworks.tools.tsc.out.IOuter;

import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class ConsoleLogImpl implements IOuter {

    @Override
    public void output(Map<String,Map<String, Set<MismatchProperties>>> result) {
        StringBuilder out = new StringBuilder();
        for (Map.Entry<String,Map<String, Set<MismatchProperties>>> value : result.entrySet()) {
            for (Map.Entry<String, Set<MismatchProperties>> entry : value.getValue().entrySet()) {
                out.append("\n-----"+entry.getKey() + " [project:"+ value.getKey()+"]\n");
                for (MismatchProperties properties : entry.getValue()) {
                    out.append(properties + "\n");
                }
            }
            out.append("\n");
        }
        Logger.getGlobal().info(out.toString());
    }
}

package com.thoughtworks.tools.tsc.out;

import com.thoughtworks.tools.tsc.model.MismatchProperties;

import java.util.Map;
import java.util.Set;

public interface IOuter {

    void output(Map<String, Map<String, Set<MismatchProperties>>> result);

}

package com.spartansoftwareinc.otter;

import java.util.HashMap;
import java.util.Map;

public class TU {
    private Map<String, TUV> tuvs = new HashMap<String, TUV>();
    
    public TU() {
    }
    
    public void addTUV(TUV tuv) {
        tuvs.put(tuv.getLocale(), tuv);
    }
    
    public Map<String, TUV> getTuvs() {
        return tuvs;
    }
}

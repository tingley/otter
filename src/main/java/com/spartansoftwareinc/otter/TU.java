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
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof TU)) return false;
        TU tu = (TU)o;
        return tuvs.equals(tu.tuvs);
    }
}

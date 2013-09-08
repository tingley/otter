package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.Util.eq;

public class PhTag extends StandaloneTag {
    private String assoc;
    
    public PhTag(String initialCodeData) {
        super(initialCodeData);
    }
    
    public PhTag(int x, String initialCodeData) {
        super(x, initialCodeData);
    }
    
    public PhTag() {
        super();
    }
    
    public String getAssoc() {
        return assoc;
    }

    public void setAssoc(String assoc) {
        this.assoc = assoc;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) &&
               (o instanceof PhTag) && 
               eq(getAssoc(), ((PhTag)o).getAssoc());
    }
    
    @Override
    public String toString() {
        return "PH('" + getContents() + "', x=" + getX() + ", type=" 
                + getType() + ", assoc=" + getAssoc() + ")";
    }
}

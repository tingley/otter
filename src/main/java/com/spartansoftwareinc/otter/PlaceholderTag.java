package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.Util.eq;

public class PlaceholderTag extends StandaloneTag {
    private String assoc;
    
    public PlaceholderTag(String initialCodeData) {
        super(initialCodeData);
    }
    
    public PlaceholderTag(int x, String initialCodeData) {
        super(x, initialCodeData);
    }
    
    public PlaceholderTag() {
        super();
    }
    
    public String getAssoc() {
        return assoc;
    }

    public void setAssoc(String assoc) {
        this.assoc = assoc;
    }

    @Override
    public int hashCode() {
        return new Hasher(super.hashCode())
            .add(assoc)
            .hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        return super.equals(o) &&
               (o instanceof PlaceholderTag) && 
               eq(getAssoc(), ((PlaceholderTag)o).getAssoc());
    }
    
    @Override
    public String toString() {
        return "PH('" + getContents() + "', x=" + getX() + ", type=" 
                + getType() + ", assoc=" + getAssoc() + ")";
    }
}

package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.Util.eq;

public class PlaceholderTag extends StandaloneTag {
    private String assoc;
    
    public PlaceholderTag(String initialCodeData) {
        super(initialCodeData);
    }
    public PlaceholderTag(ComplexContent content) {
        super(content);
    }

    public PlaceholderTag(int x, String initialCodeData) {
        super(x, initialCodeData);
    }
    
    public PlaceholderTag(int x, ComplexContent content) {
        super(x, content);
    }
    
    public PlaceholderTag(int x, String type, String initialCodeData) {
        super(x, type, initialCodeData);
    }
    
    public PlaceholderTag(int x, String type, ComplexContent content) {
        super(x, type, content);
    }
    
    public PlaceholderTag() {
        super();
    }
    
    public String getAssoc() {
        return assoc;
    }

    public PlaceholderTag setAssoc(String assoc) {
        this.assoc = assoc;
        return this;
    }
    
    public PlaceholderTag setX(int x) {
        super.setX(x);
        return this;
    }

    public PlaceholderTag setType(String type) {
        super.setType(type);
        return this;
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

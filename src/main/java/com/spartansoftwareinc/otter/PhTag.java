package com.spartansoftwareinc.otter;

// TODO: hashcode
public class PhTag extends TypedTag {
    private String assoc;
    
    public PhTag(String data) {
        super(data);
    }
    
    public PhTag(int x, String data) {
        super(x, data);
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
        return "PH('" + getData() + "', x=" + getX() + ", type=" 
                + getType() + ", assoc=" + getAssoc() + ")";
    }
}

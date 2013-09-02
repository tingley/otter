package com.spartansoftwareinc.otter;

// TODO: hashcode
public class PhTag extends StandaloneTag {
    private String type;
    private String assoc;
    
    public PhTag() {
        super();
    }
    
    public PhTag(int x) {
        super(x);
    }
    
    public PhTag(String data) {
        super(data);
    }
    
    public PhTag(int x, String data) {
        super(x, data);
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAssoc() {
        return assoc;
    }

    public void setAssoc(String assoc) {
        this.assoc = assoc;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof PhTag)) return false;
        PhTag ph = (PhTag)o;
        return eq(getX(), ph.getX()) &&
               eq(getData(), ph.getData()) &&
               eq(getType(), ph.getType()) &&
               eq(getAssoc(), ph.getAssoc());
    }
    
    @Override
    public String toString() {
        return "PH('" + getData() + "', x=" + getX() + ", type=" 
                + getType() + ", assoc=" + getAssoc() + ")";
    }
}

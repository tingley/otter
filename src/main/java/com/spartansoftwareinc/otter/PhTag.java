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
        return super.equals(o) &&
                (o instanceof PhTag) && 
               eq(getType(), ((PhTag)o).getType()) &&
               eq(getAssoc(), ((PhTag)o).getAssoc());
    }
    
    @Override
    public String toString() {
        return "PH('" + getData() + "', x=" + getX() + ", type=" 
                + getType() + ", assoc=" + getAssoc() + ")";
    }
}

package com.spartansoftwareinc.otter;

public class BptTag extends PairedTag implements NumberedTag {
    private int x = NO_VALUE;
    private String type;
    
    public BptTag(int x, int i, String data) {
        super(i, data);
        this.x = x;
    }

    public BptTag(int i, String data) {
        super(i, data);
    }

    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) 
            return false;
        if (!(o instanceof BptTag)) return false;
        BptTag bpt = (BptTag)o;
        return getX() == bpt.getX() && eq(getType(), bpt.getType());
    }
    
    @Override
    public String toString() {
        return "BPT(x=" + x + ", i=" + getI() + ", type=" + type + ", data='" + getData() + "')";
    }
}

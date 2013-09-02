package com.spartansoftwareinc.otter;

public abstract class PairedTag extends InlineTag {

    private int i = NO_VALUE;
    
    PairedTag(int i) {
        this.i = i;
    }
    PairedTag(String data) {
        super(data);
    }
    PairedTag(int i, String data) {
        super(data);
        this.i = i;
    }
    
    public int getI() {
        return i;
    }
    
    public void setI(int i) {
        this.i = i;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof PairedTag)) return false;
        PairedTag pt = (PairedTag)o;
        return getData().equals(pt.getData()) && getI() == pt.getI();
    }
}

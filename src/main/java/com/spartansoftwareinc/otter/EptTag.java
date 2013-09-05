package com.spartansoftwareinc.otter;

public class EptTag extends PairedTag {
    public EptTag(int i) {
        super(i);
    }
    
    public EptTag(int i, String initialCodes) {
        super(i, initialCodes);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && (o instanceof EptTag);
    }
    
    @Override
    public String toString() {
        return "EPT(i=" + getI() + ", data='" + getContents() + "')";
    }

}

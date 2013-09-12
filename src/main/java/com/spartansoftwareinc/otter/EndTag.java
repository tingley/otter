package com.spartansoftwareinc.otter;

public class EndTag extends PairedTag {
    public EndTag(int i) {
        super(i);
    }
    
    public EndTag(int i, String initialCodes) {
        super(i, initialCodes);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && (o instanceof EndTag);
    }
    
    @Override
    public String toString() {
        return "EPT(i=" + getI() + ", data='" + getContents() + "')";
    }

}

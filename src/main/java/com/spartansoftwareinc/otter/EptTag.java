package com.spartansoftwareinc.otter;

public class EptTag extends PairedTag {
    public EptTag(int i, String data) {
        super(i, data);
    }
    
    @Override
    public boolean equals(Object o) {
        return super.equals(o) && (o instanceof EptTag);
    }
}

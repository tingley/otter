package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.Util.eq;

/**
 * A <code>&lt;hi&gt;</code> tag.
 */
public class HighlightTag extends BaseTUVContentSink implements TUVContent, NumberedTag {
    static final int NO_VALUE = 0;
    private int x = NO_VALUE;
    private String type;
    
    public HighlightTag() {
    }
    public HighlightTag(int x) {
        this.x = x;
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
    public int hashCode() {
        return new Hasher(super.hashCode())
            .add(x)
            .add(type)
            .value();
    }
    
    @Override
    public boolean equals(Object o) {
        return super.equals(o) && 
                (o instanceof HighlightTag) &&
                x == ((HighlightTag)o).x &&
                eq(type, ((HighlightTag)o).type);
    }
}

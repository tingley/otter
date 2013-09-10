package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.Util.eq;

/**
 * Base class for standalone inline tags.
 */
public abstract class StandaloneTag extends InlineTag implements NumberedTag {
    private String type;
    private int x = NO_VALUE;

    protected StandaloneTag() {
        super();
    }
    protected StandaloneTag(String initalCodeData) {
        super(initalCodeData);
    }
    protected StandaloneTag(int x) {
        this.x = x;
    }
    protected StandaloneTag(int x, String data) {
        super(data);
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
                (o instanceof StandaloneTag) &&
                x == ((StandaloneTag)o).getX() &&
                eq(type, ((StandaloneTag)o).getType());
    }
}

package com.spartansoftwareinc.otter;

/**
 * Base class for standalone inline tags.
 */
public abstract class StandaloneTag extends InlineTag implements NumberedTag {    
    private int x = NO_VALUE;

    protected StandaloneTag() {
        super();
    }
    protected StandaloneTag(String data) {
        super(data);
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
    
    @Override
    public boolean equals(Object o) {
        return super.equals(o) && 
                (o instanceof StandaloneTag) &&
                x == ((StandaloneTag)o).getX();
    }
}

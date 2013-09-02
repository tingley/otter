package com.spartansoftwareinc.otter;

public class NumberedInlineTag extends InlineTag {
    static final int NO_ID = 0;
    
    private int x = NO_ID;

    protected NumberedInlineTag() {
        super();
    }
    protected NumberedInlineTag(String data) {
        super(data);
    }
    protected NumberedInlineTag(int x) {
        this.x = x;
    }
    protected NumberedInlineTag(int x, String data) {
        super(data);
        this.x = x;
    }
    
    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
}

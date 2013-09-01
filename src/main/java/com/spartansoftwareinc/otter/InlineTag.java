package com.spartansoftwareinc.otter;

public abstract class InlineTag implements TUVContent {

    static final int NO_ID = 0;
    
    private int x = NO_ID;
    private String data = "";
    
    protected InlineTag() {
    }
    protected InlineTag(int x) {
        this.x = x;
    }
    protected InlineTag(String data) {
        this.data = data;
    }
    protected InlineTag(int x, String data) {
        this.x = x;
        this.data = data;
    }
    
    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setData(String data) {
        this.data = (data == null) ? "" : data;
    }
    
    public String getData() {
        return data;
    }
}

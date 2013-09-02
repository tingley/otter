package com.spartansoftwareinc.otter;

public abstract class InlineTag implements TUVContent {

    private String data = "";
    
    protected InlineTag() {
    }
    protected InlineTag(String data) {
        this.data = data;
    }

    public void setData(String data) {
        this.data = (data == null) ? "" : data;
    }
    
    public String getData() {
        return data;
    }
}

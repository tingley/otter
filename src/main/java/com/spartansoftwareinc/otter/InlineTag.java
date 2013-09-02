package com.spartansoftwareinc.otter;

public abstract class InlineTag implements TUVContent {
    static final int NO_VALUE = 0;

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
    
    protected boolean eq(Object o1, Object o2) {
        if (o1 == null && o2 == null) return true;
        if (o1 != null && o2 != null) return o1.equals(o2);
        return false;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof InlineTag)) return false;
        return eq(data, ((InlineTag)o).getData());
    }

}

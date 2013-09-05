package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.Util.eq;

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
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof InlineTag)) return false;
        return eq(data, ((InlineTag)o).getData());
    }

}

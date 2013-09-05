package com.spartansoftwareinc.otter;

import java.util.ArrayList;
import java.util.List;

/**
 * A <code>&lt;hi&gt;</code> tag.
 */
public class HiTag implements TUVContent, NumberedTag {
    static final int NO_VALUE = 0;
    private int x = NO_VALUE;
    private String type;
    private List<TUVContent> contents = new ArrayList<TUVContent>();
    
    public HiTag() {
    }
    public HiTag(int x) {
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

    public List<TUVContent> getContents() {
        return contents;
    }
    
    public void addContent(TUVContent content) {
        contents.add(content);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof HiTag)) return false;
        HiTag hi = (HiTag)o;
        return x == hi.x &&
            eq(type, hi.type) &&
            contents.equals(((HiTag)o).contents);
    }

    // TODO: factor out to static
    protected boolean eq(Object o1, Object o2) {
        if (o1 == null && o2 == null) return true;
        if (o1 != null && o2 != null) return o1.equals(o2);
        return false;
    }

}

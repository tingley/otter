package com.spartansoftwareinc.otter;

import java.util.ArrayList;
import java.util.List;
import static com.spartansoftwareinc.otter.Util.eq;

/**
 * A <code>&lt;hi&gt;</code> tag.
 */
public class HiTag implements TUVContent, NumberedTag, TUVContentSink {
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
    public int hashCode() {
        return new Hasher()
            .add(x)
            .add(type)
            .add(contents)
            .value();
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
}

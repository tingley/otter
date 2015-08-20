package com.spartansoftwareinc.otter;

import java.util.Objects;

/**
 * A representation of a <code>&lt;hi&gt;</code> tag.
 * <br>
 * The {@link #addContent(TUVContent)} method for HighlightTag will throw
 * an exception if it is passed anything other than {@link TextContent}, 
 * {@link InlineTag}, and {@link HighlightTag} content items.
 */
public class HighlightTag extends BaseTUVContentSink implements NumberedTag {
    static final int NO_VALUE = 0;
    private int x = NO_VALUE;
    private String type;
    
    public HighlightTag() {
    }

    public int getX() {
        return x;
    }
    
    public HighlightTag setX(int x) {
        this.x = x;
        return this;
    }
    
    public String getType() {
        return type;
    }
    
    public HighlightTag setType(String type) {
        this.type = type;
        return this;
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
                Objects.equals(type, ((HighlightTag)o).type);
    }
}

package com.spartansoftwareinc.otter;

import java.util.ArrayList;
import java.util.List;
import static com.spartansoftwareinc.otter.Util.eq;

/**
 * A <code>&lt;hi&gt;</code> tag.
 */
public class HighlightTag implements TUVContent, NumberedTag, TUVContentSink {
    static final int NO_VALUE = 0;
    private int x = NO_VALUE;
    private String type;
    private List<TUVContent> contents = new ArrayList<TUVContent>();
    
    public HighlightTag() {
    }
    public HighlightTag(int x) {
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
    
    /**
     * Add an item to the contents of this tag.  HighlightTag objects
     * are restricted to {@link TextContent}, {@link InlineTag}, and
     * {@link HighlightTag} content items.
     * 
     * @param content content item to add to this tag
     * @throws IllegalArgumentException if an invalid content item is added 
     */
    public void addContent(TUVContent content) {
        if (content instanceof Subflow) {
            throw new IllegalArgumentException("Subflow element not allowed in this location");
        }
        if (content instanceof CodeContent) {
            throw new IllegalArgumentException("CodeContent element not allowed in this location");
        }
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
        if (o == null || !(o instanceof HighlightTag)) return false;
        HighlightTag hi = (HighlightTag)o;
        return x == hi.x &&
            eq(type, hi.type) &&
            contents.equals(((HighlightTag)o).contents);
    }
}

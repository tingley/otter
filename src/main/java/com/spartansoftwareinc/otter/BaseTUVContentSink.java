package com.spartansoftwareinc.otter;

import java.util.ArrayList;
import java.util.List;

public class BaseTUVContentSink implements TUVContentSink {

    private List<TUVContent> contents = new ArrayList<TUVContent>();
    
    /**
     * Add an item to the contents of this tag.  TUV objects
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

    List<TUVContent> getContents() {
        return contents;
    }
    
    @Override
    public int hashCode() {
        return new Hasher()
            .add(contents)
            .value();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof BaseTUVContentSink)) return false;
        BaseTUVContentSink tuv = (BaseTUVContentSink)o;
        return contents.equals(tuv.contents);
    }
}

package com.spartansoftwareinc.otter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Base class for things that accept "text" TUV content. 
 */
public abstract class BaseTUVContentSink implements TUVContentSink {

    private List<TUVContent> contents = new ArrayList<TUVContent>();
    
    /**
     * Add an item to the contents of this object.  TUV objects
     * are restricted to {@link TextContent}, {@link InlineTag}, and
     * {@link HighlightTag} content items.
     * 
     * @param content content item to add to this object
     * @throws IllegalArgumentException if an invalid content item is added 
     */
    public BaseTUVContentSink addContent(TUVContent content) {
        if (content instanceof Subflow) {
            throw new IllegalArgumentException("Subflow element not allowed in this location");
        }
        if (content instanceof CodeContent) {
            throw new IllegalArgumentException("CodeContent element not allowed in this location");
        }
        contents.add(content);
        return this;
    }
    
    /**
     * Add multiple items to the contents of this object.  TUV objects
     * are restricted to {@link TextContent}, {@link InlineTag}, and
     * {@link HighlightTag} content items.
     * 
     * @param contents
     */
    public BaseTUVContentSink addContents(List<TUVContent> contents) {
    	for (TUVContent content : contents) {
    		addContent(content);
    	}
    	return this;
    }
    
    /**
     * Get the contents of this object.
     * @return list of {@link TUVContent} items
     */
    public List<TUVContent> getContents() {
        return contents;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(contents);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof BaseTUVContentSink)) return false;
        BaseTUVContentSink tuv = (BaseTUVContentSink)o;
        return contents.equals(tuv.contents);
    }
}

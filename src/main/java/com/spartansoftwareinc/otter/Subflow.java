package com.spartansoftwareinc.otter;

import java.util.ArrayList;
import java.util.List;
import static com.spartansoftwareinc.otter.Util.eq;

/**
 * A representation of a TUV subflow.  The contents of the subflow
 * correspond to the portion of the TUV delimited by &lt;sub&gt; tags.
 * Subflows can contain a mix of text content and inline tags.
 */
public class Subflow implements TUVContent, TUVContentSink {
    private String type, datatype;
    private List<TUVContent> contents = new ArrayList<TUVContent>();
    
    public Subflow() {
    }
    
    /**
     * Add an item to the contents of this tag.  Subflow objects
     * are restricted to {@link TextContent}, {@link InlineTag}, and
     * {@link HighlightTag} content items.
     * 
     * @param content content item to add to this tag
     * @throws IllegalArgumentException if an invalid content item is added 
     */
    @Override
    public void addContent(TUVContent content) {
        if (content instanceof Subflow) {
            throw new IllegalArgumentException("Subflow element not allowed in this location");
        }
        if (content instanceof CodeContent) {
            throw new IllegalArgumentException("CodeContent element not allowed in this location");
        }
        contents.add(content);
    }

    public List<TUVContent> getContents() {
        return contents;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }
    
    @Override
    public int hashCode() {
        return new Hasher()
            .add(type)
            .add(datatype)
            .add(contents)
            .value();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof Subflow)) return false;
        Subflow sub = (Subflow)o;
        return eq(type, sub.type) &&
               eq(datatype, sub.datatype) &&
               contents.equals(sub.contents);
    }
}

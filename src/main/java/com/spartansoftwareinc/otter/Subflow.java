package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.Util.eq;

import java.util.List;

/**
 * A representation of a TUV subflow.  The contents of the subflow
 * correspond to the portion of the TUV delimited by &lt;sub&gt; tags.
 * Subflows can contain a mix of text content and inline tags.
 */
public class Subflow extends BaseTUVContentSink implements TagContent {
    private String type, datatype;

    public Subflow() {
    }

    /**
     * Add an item to the contents of this Subflow.  Subflows
     * are restricted to {@link TextContent}, {@link InlineTag}, and
     * {@link HighlightTag} content items.
     *
     * @param content content item to add to this object
     * @throws IllegalArgumentException if an invalid content item is added 
     */
    public Subflow addContent(TUVContent content) {
        return (Subflow)super.addContent(content);
    }

    /**
     * Ad multiple items to the contents of this Subflow.  Subflows
     * are restricted to {@link TextContent}, {@link InlineTag}, and
     * {@link HighlightTag} content items.
     *
     * @param contents
     */
    public Subflow addContents(List<TUVContent> contents) {
        return (Subflow)super.addContents(contents);
    }

    /**
     * Get the value of the <code>type</code> attribute for this
     * subflow.
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Set the value of the <code>type</code> attribute for this 
     * subflow.
     * @param type
     * @return
     */
    public Subflow setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get the value of the <code>datatype</code> attribute for this 
     * subflow.
     * @return
     */
    public String getDatatype() {
        return datatype;
    }

    /**
     * Set the value of the <code>datatype</code> attribute for this subflow.
     * @param datatype
     */
    public Subflow setDatatype(String datatype) {
        this.datatype = datatype;
        return this;
    }

    @Override
    public int hashCode() {
        return new Hasher(super.hashCode())
            .add(type)
            .add(datatype)
            .value();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && 
                (o instanceof Subflow) &&
                eq(type, ((Subflow)o).type) &&
                eq(datatype, ((Subflow)o).datatype);
    }
}

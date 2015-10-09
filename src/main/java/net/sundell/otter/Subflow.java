package net.sundell.otter;

import java.util.List;
import java.util.Objects;

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
     * @return updated Subflow instance 
     */
    public Subflow addContent(TUVContent content) {
        return (Subflow)super.addContent(content);
    }

    /**
     * Ad multiple items to the contents of this Subflow.  Subflows
     * are restricted to {@link TextContent}, {@link InlineTag}, and
     * {@link HighlightTag} content items.
     *
     * @param contents content items to add to this subflow
     * @throws IllegalArgumentException if an invalid content item is added
     * @return updated Subflow instance
     */
    public Subflow addContents(List<TUVContent> contents) {
        return (Subflow)super.addContents(contents);
    }

    /**
     * Get the value of the <code>type</code> attribute for this
     * subflow.
     * @return type for this subflow
     */
    public String getType() {
        return type;
    }

    /**
     * Set the value of the <code>type</code> attribute for this 
     * subflow.
     * @param type new type value
     * @return updated Subflow instance
     */
    public Subflow setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get the value of the <code>datatype</code> attribute for this 
     * subflow.
     * @return datatype for this subflow
     */
    public String getDatatype() {
        return datatype;
    }

    /**
     * Set the value of the <code>datatype</code> attribute for this subflow.
     * @param datatype new datatype value
     * @return updated Subflow instance
     */
    public Subflow setDatatype(String datatype) {
        this.datatype = datatype;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), type, datatype);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && 
                (o instanceof Subflow) &&
                Objects.equals(type, ((Subflow)o).type) &&
                Objects.equals(datatype, ((Subflow)o).datatype);
    }
}

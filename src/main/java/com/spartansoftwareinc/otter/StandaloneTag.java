package com.spartansoftwareinc.otter;

import java.util.List;
import java.util.Objects;

/**
 * Base class for standalone inline tags.
 */
public abstract class StandaloneTag extends InlineTag implements NumberedTag {
    private String type;
    private int x = NO_VALUE;

    protected StandaloneTag() {
        super();
    }
    protected StandaloneTag(String initalCodeData) {
        super(initalCodeData);
    }
    protected StandaloneTag(ComplexContent content) {
        super(content);
    }
    
    /**
     * Add an item to the contents of this tag.  StandaloneTag objects
     * are restricted to {@link CodeContent} and {@link Subflow} content
     * items.
     * 
     * @param content content item to add to this tag
     * @throws IllegalArgumentException if an invalid content item is added
     * @return updated StandaloneTag instance 
     */
    @Override
    public StandaloneTag addContent(TUVContent content) {
        return (StandaloneTag)super.addContent(content);
    }

    /**
     * Add multiple items to the contents of this tag.  StandaloneTag objects
     * are restricted to {@link CodeContent} and {@link Subflow} content
     * items.
     * 
     * @param contents content items to add to this tag
     * @throws IllegalArgumentException if an invalid content item is added
     * @return updated StandaloneTag instance
     */
    public StandaloneTag addContents(List<TUVContent> contents) {
        return (StandaloneTag)super.addContents(contents);
    }

    /**
     * Return the value of the <code>x</code> attribute for this tag.
     * @return value of <code>x</code> attribute
     */
    public int getX() {
        return x;
    }
    
    /**
     * Set the value of the <code>x</code> attribute for this tag
     * @param x new value
     * @return updated StandaloneTag instance
     */
    public StandaloneTag setX(int x) {
        this.x = x;
        return this;
    }
    
    /**
     * Return the value of the <code>type</code> attribute for this tag.
     * @return value of <code>type</code> attribute
     */
    public String getType() {
        return type;
    }

    /**
     * Set the value of the <code>type</code> attribute for this tag
     * @param type new type value
     * @return updated StandaloneTag instance
     */
    public StandaloneTag setType(String type) {
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
                (o instanceof StandaloneTag) &&
                x == ((StandaloneTag)o).getX() &&
                Objects.equals(type, ((StandaloneTag)o).getType());
    }
}

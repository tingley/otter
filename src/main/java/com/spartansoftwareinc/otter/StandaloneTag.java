package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.Util.eq;

import java.util.List;

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
    protected StandaloneTag(int x) {
        this.x = x;
    }
    protected StandaloneTag(int x, String data) {
        super(data);
        this.x = x;
    }
    protected StandaloneTag(int x, ComplexContent content) {
        super(content);
        this.x = x;
    }
    protected StandaloneTag(int x, String type, String data) {
        super(data);
        this.x = x;
        this.type = type;
    }
    public StandaloneTag(int x, String type, ComplexContent content) {
        super(content);
        this.x = x;
        this.type = type;
    }
    
    /**
     * Add an item to the contents of this tag.  StandaloneTag objects
     * are restricted to {@link CodeContent} and {@link Subflow} content
     * items.
     * 
     * @param content content item to add to this tag
     * @throws IllegalArgumentException if an invalid content item is added 
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
     * @param contents
     */
    public StandaloneTag addContents(List<TUVContent> contents) {
        return (StandaloneTag)super.addContents(contents);
    }

    public int getX() {
        return x;
    }
    
    public StandaloneTag setX(int x) {
        this.x = x;
        return this;
    }
    
    public String getType() {
        return type;
    }

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
                eq(type, ((StandaloneTag)o).getType());
    }
}

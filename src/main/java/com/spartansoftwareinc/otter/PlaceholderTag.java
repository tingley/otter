package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.Util.eq;

import java.util.List;

public class PlaceholderTag extends StandaloneTag {
    private String assoc;
    
    public PlaceholderTag() {
        super();
    }
    public PlaceholderTag(String initialCodeData) {
        super(initialCodeData);
    }
    public PlaceholderTag(ComplexContent content) {
        super(content);
    }
    
    /**
     * Add an item to the contents of this tag.  PlaceholderTag objects
     * are restricted to {@link CodeContent} and {@link Subflow} content
     * items.
     * 
     * @param content content item to add to this tag
     * @throws IllegalArgumentException if an invalid content item is added 
     */
    @Override
    public PlaceholderTag addContent(TUVContent content) {
        return (PlaceholderTag)super.addContent(content);
    }

    /**
     * Add multiple items to the contents of this tag.  PlaceholderTag objects
     * are restricted to {@link CodeContent} and {@link Subflow} content
     * items.
     * 
     * @param contents
     */
    public PlaceholderTag addContents(List<TUVContent> contents) {
        return (PlaceholderTag)super.addContents(contents);
    }

    public String getAssoc() {
        return assoc;
    }

    public PlaceholderTag setAssoc(String assoc) {
        this.assoc = assoc;
        return this;
    }
    
    public PlaceholderTag setX(int x) {
        super.setX(x);
        return this;
    }

    public PlaceholderTag setType(String type) {
        super.setType(type);
        return this;
    }

    @Override
    public int hashCode() {
        return new Hasher(super.hashCode())
            .add(assoc)
            .hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        return super.equals(o) &&
               (o instanceof PlaceholderTag) && 
               eq(getAssoc(), ((PlaceholderTag)o).getAssoc());
    }
    
    @Override
    public String toString() {
        return "PH('" + getContents() + "', x=" + getX() + ", type=" 
                + getType() + ", assoc=" + getAssoc() + ")";
    }
}

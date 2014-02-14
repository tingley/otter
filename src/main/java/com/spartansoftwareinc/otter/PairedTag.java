package com.spartansoftwareinc.otter;

import java.util.List;

/**
 * Base class for paired tags. Paired tags support the <code>i</code>
 * attribute, which is used to identify corresponding pairs of 
 * {@link BeginTag} and {@link EndTag}.
 * <br>
 * The {@link #addContent} method for PairedTag will throw
 * an exception if it is passed anything other than {@link CodeContent} 
 * or {@link Subflow} objects.
 */
public abstract class PairedTag extends InlineTag {
    private int i = NO_VALUE;
    
    PairedTag(int i) {
        this.i = i;
    }
    PairedTag(String codeData) {
        super(codeData);
    }
    PairedTag(int i, String data) {
        super(data);
        this.i = i;
    }
    PairedTag(int i, ComplexContent content) {
        super(content);
        this.i = i;
    }

    @Override
    boolean hasRequiredAttributes() {
        return (i != NO_VALUE);
    }

    /**
     * Add an item to the contents of this tag.  PairedTag objects
     * are restricted to {@link CodeContent} and {@link Subflow} content
     * items.
     * 
     * @param content content item to add to this tag
     * @throws IllegalArgumentException if an invalid content item is added
     * @return this PairedTag instance
     */
    @Override
    public PairedTag addContent(TUVContent content) {
        return (PairedTag)super.addContent(content);
    }

    /**
     * Ad multiple items to the contents of this tag.  PairedTag objects
     * are restricted to {@link TextContent}, {@link InlineTag}, and
     * {@link HighlightTag} content items.
     * 
     * @param contents content items to be added to this tag
     * @throws IllegalArgumentException if an invalid content item is added
     * @return this PairedTag instance
     */
    public PairedTag addContents(List<TUVContent> contents) {
        return (PairedTag)super.addContents(contents);
    }

    /**
     * Get the value of the <code>i</code> attribute for this tag.
     * @return value of the <code>i</code> attribute
     */
    public int getI() {
        return i;
    }

    /**
     * Set the value of the <code>i</code> attribute for this tag.
     * @param i new <code>i</code> value
     * @return this PairedTag instance
     */
    public PairedTag setI(int i) {
        this.i = i;
        return this;
    }

    @Override
    public int hashCode() {
        return new Hasher(super.hashCode())
            .add(i)
            .value();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof PairedTag)) return false;
        PairedTag pt = (PairedTag)o;
        return getContents().equals(pt.getContents()) && getI() == pt.getI();
    }
}

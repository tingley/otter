package com.spartansoftwareinc.otter;

import java.util.List;

/**
 * A representation of a <code>&lt;ept&gt;</code> tag.  Every EndTag in a 
 * container must appear after a {@link BeginTag} with the same <code>i</code>
 * value.
 * <br>
 * The {@link #addContent} method for EndTag will throw
 * an exception if it is passed anything other than {@link CodeContent} 
 * or {@link Subflow} objects.
 */
public class EndTag extends PairedTag {
    /**
     * Constructor, specifying only the <code>i</code> attribute value.
     * @param i value of the <code>i</code> attribute for this tag
     */
    public EndTag(int i) {
        super(i);
    }
    
    /**
     * Constructor, specifying the <code>i</code> 
     * attribute value and the native code data for this tag. 
     * @param i value of the required <code>i</code> attribute
     * @param codeData native code data for this tag
     */
    public EndTag(int i, String initialCodes) {
        super(i, initialCodes);
    }

    /**
     * Add an item to the contents of this tag.  EndTag objects
     * are restricted to {@link CodeContent} and {@link Subflow} content
     * items.
     * 
     * @param content content item to add to this tag
     * @throws IllegalArgumentException if an invalid content item is added 
     */
    @Override
    public EndTag addContent(TUVContent content) {
        return (EndTag)super.addContent(content);
    }

    /**
     * Ad multiple items to the contents of this tag.  EndTag objects
     * are restricted to {@link CodeContent} and {@link Subflow} content
     * items.
     * 
     * @param contents
     */
    public EndTag addContents(List<TUVContent> contents) {
        return (EndTag)super.addContents(contents);
    }

    /**
     * Set the value of the <code>i</code> attribute for this tag.
     * @param i new <code>i</code> value
     * @return
     */
    public EndTag setI(int i) {
        return (EndTag)super.setI(i);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && (o instanceof EndTag);
    }
    
    @Override
    public String toString() {
        return "EPT(i=" + getI() + ", data='" + getContents() + "')";
    }

}

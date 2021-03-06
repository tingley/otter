package net.sundell.otter;

import java.util.List;
import java.util.Objects;

/**
 * A representation of a <code>&lt;bpt&gt;</code> tag.  A container that
 * includes a BeginTag must also include a subsequent {@link EndTag} with the
 * matching <code>i</code> value.
 * <br>
 * The {@link #addContent} method for BeginTag will throw
 * an exception if it is passed anything other than {@link CodeContent} 
 * or {@link Subflow} objects.
 */
public class BeginTag extends PairedTag implements NumberedTag {
    private int x = NO_VALUE;
    private String type;

    /**
     * Constructor, specifying only the <code>i</code> attribute value.
     * @param i value of the <code>i</code> attribute for this tag
     */
    public BeginTag(int i) {
        super(i);
    }
    /**
     * Constructor, specifying the <code>i</code> and <code>x</code>
     * attribute values and the native code data for this tag.
     * @param i value of the required <code>i</code> attribute
     * @param codeData native code data for this tag
     */
    public BeginTag(int i, String codeData) {
        super(i, codeData);
    }

    /**
     * Constructor, specifying the <code>i</code> and <code>x</code>
     * attribute values and the complex code content for  this tag.
     * @param i value of the required <code>i</code> attribute
     * @param content complex content for this tag
     */
    public BeginTag(int i, ComplexContent content) {
        super(i, content);
    }
    
    /**
     * Add an item to the contents of this tag.  BeginTag objects
     * are restricted to {@link CodeContent} and {@link Subflow} content
     * items.
     * 
     * @param content content item to add to this tag
     * @throws IllegalArgumentException if an invalid content item is added 
     * @return this BeginTag instance
     */
    @Override
    public BeginTag addContent(TUVContent content) {
        return (BeginTag)super.addContent(content);
    }

    /**
     * Add multiple items to the contents of this tag.  BeginTag objects
     * are restricted to {@link CodeContent} and {@link Subflow} content
     * items.
     * 
     * @param contents content items to add to this tag
     * @return this BeginTag instance
     */
    public BeginTag addContents(List<TUVContent> contents) {
        return (BeginTag)super.addContents(contents);
    }

    /**
     * Set the value of the <code>i</code> attribute for this tag.
     * @param i new <code>i</code> value
     * @return this BeginTag instance
     */
    public BeginTag setI(int i) {
        return (BeginTag)super.setI(i);
    }
    
    /**
     * Get the value of the <code>x</code> attribute for this tag.
     * @return value of the <code>x</code> attribute
     */
    public int getX() {
        return x;
    }
    
    /**
     * Set the value of the <code>x</code> attribute for this tag.
     * @param x new <code>x</code> value
     * @return this BeginTag instance
     */
    public BeginTag setX(int x) {
        this.x = x;
        return this;
    }

    /**
     * Get the value of the <code>type</code> attribute for this tag.
     * @return value of the <code>type</code> attribute for this tag 
     */
    public String getType() {
        return type;
    }

    /**
     * Set the value of the <code>type</code> attribute for this tag.
     * @param type new <code>type</code> value
     * @return this BeginTag instance
     */
    public BeginTag setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), x, type);
    }

    @Override
    public boolean equals(Object o) {
        if (!super.equals(o)) 
            return false;
        if (!(o instanceof BeginTag)) return false;
        BeginTag bpt = (BeginTag)o;
        return getX() == bpt.getX() && Objects.equals(getType(), bpt.getType());
    }
    
    @Override
    public String toString() {
        return "BPT(x=" + x + ", i=" + getI() + ", type=" + type + ", data='" + getContents() + "')";
    }
}

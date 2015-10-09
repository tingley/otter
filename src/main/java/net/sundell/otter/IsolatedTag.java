package net.sundell.otter;

import java.util.List;
import java.util.Objects;

/**
 * A <code>&lt;it&gt;</code> tag.
 */
public class IsolatedTag extends StandaloneTag {
    public enum Pos {
        BEGIN("begin"),
        END("end");
        private String attrValue;
        Pos(String attrValue) {
            this.attrValue = attrValue;
        }
        public String getAttrValue() {
            return attrValue;
        }
        public static Pos byAttrValue(String v) {
            for (Pos p : values()) {
                if (p.getAttrValue().equals(v)) {
                    return p;
                }
            }
            return null;
        }
    }

    private Pos pos;

    public IsolatedTag() {
        super();
    }

    public IsolatedTag(Pos pos) {
        super();
        this.pos = pos;
    }

    public IsolatedTag(Pos pos, String initialCodeData) {
        super(initialCodeData);
        this.pos = pos;
    }

    public IsolatedTag(Pos pos, ComplexContent content) {
        super(content);
        this.pos = pos;
    }
    
    @Override
    boolean hasRequiredAttributes() {
        return pos != null;
    }
    
    /**
     * Add an item to the contents of this tag.  IsolatedTag objects
     * are restricted to {@link CodeContent} and {@link Subflow} content
     * items.
     * 
     * @param content content item to add to this tag
     * @throws IllegalArgumentException if an invalid content item is added 
     */
    @Override
    public IsolatedTag addContent(TUVContent content) {
        return (IsolatedTag)super.addContent(content);
    }

    /**
     * Add multiple items to the contents of this tag.  IsolatedTag objects
     * are restricted to {@link CodeContent} and {@link Subflow} content
     * items.
     * 
     * @param contents
     */
    public IsolatedTag addContents(List<TUVContent> contents) {
        return (IsolatedTag)super.addContents(contents);
    }
    
    public Pos getPos() {
        return pos;
    }
    
    public void setPos(Pos pos) {
        this.pos = pos;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pos);
    }
    
    @Override
    public boolean equals(Object o) {
        return super.equals(o) &&
                (o instanceof IsolatedTag) &&
                (pos == ((IsolatedTag)o).pos);
    }
    
    @Override
    public String toString() {
        return "IT(pos=" + pos + ", x=" + getX() + ", data='" + getContents() + "')";
    }

}

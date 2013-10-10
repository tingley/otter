package com.spartansoftwareinc.otter;

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
    
    public IsolatedTag(int x, String initialCodeData, Pos pos) {
        super(x, initialCodeData);
        this.pos = pos;
    }

    public IsolatedTag(String initialCodeData, Pos pos) {
        super(initialCodeData);
        this.pos = pos;
    }

    public IsolatedTag(Pos pos) {
        super();
        this.pos = pos;
    }
    
    public IsolatedTag() {
        super();
    }
    
    public Pos getPos() {
        return pos;
    }
    
    public void setPos(Pos pos) {
        this.pos = pos;
    }
    
    @Override
    public int hashCode() {
        return new Hasher(super.hashCode())
            .add(pos)
            .value();
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

package com.spartansoftwareinc.otter;

/**
 * A <code>&lt;it&gt;</code> tag.
 */
public class ItTag extends StandaloneTag {
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
    
    public ItTag(int x, String initialCodeData, Pos pos) {
        super(x, initialCodeData);
        this.pos = pos;
    }

    public ItTag(String initialCodeData, Pos pos) {
        super(initialCodeData);
        this.pos = pos;
    }

    public ItTag(Pos pos) {
        super();
        this.pos = pos;
    }
    
    public ItTag() {
        super();
    }
    
    public Pos getPos() {
        return pos;
    }
    
    public void setPos(Pos pos) {
        this.pos = pos;
    }
    
    @Override
    public boolean equals(Object o) {
        return super.equals(o) &&
                (o instanceof ItTag) &&
                (pos == ((ItTag)o).pos);
    }
}

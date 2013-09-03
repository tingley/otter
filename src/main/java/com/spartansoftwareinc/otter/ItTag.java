package com.spartansoftwareinc.otter;

/**
 * A <code>&lt;it&gt;</code> tag.
 */
public class ItTag extends TypedTag {
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
    
    public ItTag(int x, String data, Pos pos) {
        super(x, data);
        this.pos = pos;
    }

    public ItTag(String data, Pos pos) {
        super(data);
        this.pos = pos;
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

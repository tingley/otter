package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.Util.eq;

/**
 * A representation of a <code>&lt;bpt&gt;</code> tag.  A container that
 * includes a BeginTag must also include a subsequent {@link EndTag} with the
 * matching <code>i</code> value.
 * <br>
 * The {@link #addContent(TUVContent)} method for BeginTag will throw
 * an exception if it is passed anything other than {@link CodeContent} 
 * or {@link Subflow} objects.
 */
public class BeginTag extends PairedTag implements NumberedTag {
    private int x = NO_VALUE;
    private String type;
    
    public BeginTag(int x, int i) {
        super(i);
        this.x = x;
    }

    public BeginTag(int i) {
        super(i);
    }
    
    public BeginTag(int x, int i, String initialCodes) {
        super(i, initialCodes);
        this.x = x;
    }

    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
        if (!super.equals(o)) 
            return false;
        if (!(o instanceof BeginTag)) return false;
        BeginTag bpt = (BeginTag)o;
        return getX() == bpt.getX() && eq(getType(), bpt.getType());
    }
    
    @Override
    public String toString() {
        return "BPT(x=" + x + ", i=" + getI() + ", type=" + type + ", data='" + getContents() + "')";
    }
}

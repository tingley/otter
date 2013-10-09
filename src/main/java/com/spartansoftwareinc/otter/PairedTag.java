package com.spartansoftwareinc.otter;

/**
 * Base class for paired tags. Paired tags support the <code>i</code>
 * attribute, which is used to identify corresponding pairs of 
 * {@link BeginTag} and {@link EndTag}.
 * <br>
 * The {@link #addContent(TUVContent)} method for PairedTag will throw
 * an exception if it is passed anything other than {@link CodeContent} 
 * or {@link Subflow} objects.
 */
public abstract class PairedTag extends InlineTag {
    private int i = NO_VALUE;
    
    PairedTag(int i) {
        this.i = i;
    }
    PairedTag(String data) {
        super(data);
    }
    PairedTag(int i, String data) {
        super(data);
        this.i = i;
    }
    
    public int getI() {
        return i;
    }
    
    public void setI(int i) {
        this.i = i;
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

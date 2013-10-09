package com.spartansoftwareinc.otter;

/**
 * A representation of a <code>&lt;ept&gt;</code> tag.  Every EndTag in a 
 * container must appear after a {@link BeginTag} with the same <code>i</code>
 * value.
 * <br>
 * The {@link #addContent(TUVContent)} method for EndTag will throw
 * an exception if it is passed anything other than {@link CodeContent} 
 * or {@link Subflow} objects.
 */
public class EndTag extends PairedTag {
    public EndTag(int i) {
        super(i);
    }
    
    public EndTag(int i, String initialCodes) {
        super(i, initialCodes);
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

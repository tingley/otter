package com.spartansoftwareinc.otter;

/**
 * A <code>&lt;it&gt;</code> tag.
 */
public class ItTag extends StandaloneTag {
    private String type;
    
    public ItTag(int x, String data) {
        super(x, data);
    }

    public ItTag(String data) {
        super(data);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) &&
                (o instanceof ItTag) &&
                eq(type, ((ItTag)o).getType());
    }
}

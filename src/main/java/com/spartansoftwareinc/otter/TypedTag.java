package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.Util.eq;

/**
 * A standalone tag that supports the <code>type</code> attribute.
 */
public class TypedTag extends StandaloneTag {
    private String type;
    
    public TypedTag(int x, String data) {
        super(x, data);
    }

    public TypedTag(String data) {
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
                (o instanceof TypedTag) &&
                eq(type, ((TypedTag)o).getType());
    }

}

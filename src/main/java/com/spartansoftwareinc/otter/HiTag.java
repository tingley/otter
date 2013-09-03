package com.spartansoftwareinc.otter;

/**
 * A <code>&lt;hi&gt;</code> tag.
 */
public class HiTag extends TypedTag {
    public HiTag(int x, String data) {
        super(x, data);
    }

    public HiTag(String data) {
        super(data);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) &&
                (o instanceof HiTag);
    }
}

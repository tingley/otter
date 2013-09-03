package com.spartansoftwareinc.otter;

import java.util.ArrayList;
import java.util.List;

/**
 * A <code>&lt;hi&gt;</code> tag.
 */
// TODO: this should not extend the tag hierarchy because it doesn't support data
public class HiTag extends TypedTag {
    private List<TUVContent> contents = new ArrayList<TUVContent>();
    
    public HiTag(int x, String data) {
        super(x, data);
    }

    public HiTag(String data) {
        super(data);
    }

    public List<TUVContent> getContents() {
        return contents;
    }
    
    public void addContent(TUVContent content) {
        contents.add(content);
    }
    
    @Override
    public boolean equals(Object o) {
        return super.equals(o) &&
                (o instanceof HiTag) &&
                contents.equals(((HiTag)o).contents);
    }
}

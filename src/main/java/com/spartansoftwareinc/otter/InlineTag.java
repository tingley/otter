package com.spartansoftwareinc.otter;

import java.util.ArrayList;
import java.util.List;

public abstract class InlineTag implements TUVContent, TUVContentSink {
    static final int NO_VALUE = 0;
    private List<TUVContent> contents = new ArrayList<TUVContent>();
    
    protected InlineTag() {
    }
    protected InlineTag(String initialCodeContent) {
        contents.add(new CodeContent(initialCodeContent));
    }

    @Override
    public void addContent(TUVContent content) {
        if (!(content instanceof CodeContent) && !(content instanceof Subflow)) {
            throw new IllegalStateException("Illegal paired tag content: " + content);
        }
        contents.add(content);
    }
    
    public List<TUVContent> getContents() {
        return contents;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof InlineTag)) return false;
        return contents.equals(((InlineTag)o).contents);
    }
}

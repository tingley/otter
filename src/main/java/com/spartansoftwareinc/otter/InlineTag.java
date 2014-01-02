package com.spartansoftwareinc.otter;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all inline tags in TUV content that can contain 
 * native code data. 
 * <br>
 * The {@link #addContent(TUVContent)} method for InlineTags will throw
 * an exception if it is passed anything other than {@link CodeContent} 
 * or {@link Subflow} objects.
 */
public abstract class InlineTag implements TagContent {
    static final int NO_VALUE = 0;
    private List<TUVContent> contents = new ArrayList<TUVContent>();
    
    protected InlineTag() {
    }
    protected InlineTag(String initialCodeContent) {
        contents.add(new CodeContent(initialCodeContent));
    }
    protected InlineTag(ComplexContent content) {
        contents.addAll(content.getContent());
    }

    /**
     * Add an item to the contents of this tag.  InlineTag objects
     * are restricted to {@link CodeContent} and {@link Subflow} content
     * items.
     * 
     * @param content content item to add to this tag
     * @throws IllegalArgumentException if an invalid content item is added 
     */
    @Override
    public void addContent(TUVContent content) {
        if (!(content instanceof CodeContent) && !(content instanceof Subflow)) {
            throw new IllegalArgumentException("Illegal paired tag content: " + content);
        }
        contents.add(content);
    }

    /**
     * Ad multiple items to the contents of this tag.  TUV objects
     * are restricted to {@link TextContent}, {@link InlineTag}, and
     * {@link HighlightTag} content items.
     * 
     * @param contents
     */
    public void addContents(List<TUVContent> contents) {
        for (TUVContent content : contents) {
            addContent(content);
        }
    }

    public List<TUVContent> getContents() {
        return contents;
    }
    
    @Override
    public int hashCode() {
        return new Hasher()
            .add(contents)
            .value();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof InlineTag)) return false;
        return contents.equals(((InlineTag)o).contents);
    }
}

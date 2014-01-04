package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.Util.eq;

/**
 * Representation of a <code>&lt;note&gt;</code> element.  Notes
 * can be attached either to a {@link Header} or a {@link TU}.
 */
public class Note {
    private String content;

    public Note() {
    }
    
    public Note(String content) {
        this.content = content;
    }

    /**
     * Get the note content.
     * @return
     */
    public String getContent() {
        return content;
    }

    /**
     * Set the note content.
     * @param content
     */
    public Note setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public int hashCode() {
        return new Hasher()
            .add(content)
            .value();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof Note)) return false;
        Note n = (Note)o;
        return eq(content, n.content);
    }
}

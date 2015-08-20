package com.spartansoftwareinc.otter;

import java.util.Objects;

/**
 * Representation of a <code>&lt;note&gt;</code> element.  Notes
 * can be attached either to a {@link Header} or a {@link TU}.
 */
public class Note {
    private String content;
    private String encoding, lang;

    /**
     * Construct an empty note.
     */
    public Note() {
    }
    
    /**
     * Construct a note with the specified content.
     * @param content content of this note.
     */
    public Note(String content) {
        this.content = content;
    }

    /**
     * Get the note content.
     * @return content of this note
     */
    public String getContent() {
        return content;
    }

    /**
     * Set the note content.
     * @param content new content for this Note
     * @return this Note instance
     */
    public Note setContent(String content) {
        this.content = content;
        return this;
    }
    
    /**
     * Get the value of the <code>o-encoding</code> attribute
     * for this property, if present.
     * @return encoding value 
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Set the value of the <code>o-encoding</code> attribute for
     * this note.  No validation is performed.
     * @param encoding encoding for this note
     * @return this Note instance
     */
    public Note setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }
    
    /**
     * Get the value of the <code>xml:lang</code> attribute
     * for this note, if present.
     * @return xml:lang value
     */
    public String getLang() {
        return lang;
    }

    /**
     * Set the value of the <code>xml:lang</code> attribute for
     * this note.  No validation is performed.
     * @param lang xml:lang value for this note
     * @return this Note instance
     */
    public Note setLang(String lang) {
        this.lang = lang;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, encoding, lang);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof Note)) return false;
        Note n = (Note)o;
        return Objects.equals(content, n.content) &&
               Objects.equals(encoding, n.encoding) &&
               Objects.equals(lang, n.lang);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Note(content='" + content+ "'");
        if (encoding != null) {
            sb.append(" o-encoding='" + encoding + "'");
        }
        if (lang != null) {
            sb.append(" xml:lang='" + lang + "'");
        }
        sb.append(")");
        return sb.toString();
    }
}

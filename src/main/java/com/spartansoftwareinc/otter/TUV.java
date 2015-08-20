package com.spartansoftwareinc.otter;

import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

/**
 * Representation of a <code>&lt;tuv&gt;</code> element and all 
 * of its child content.  A <code>TUV</code> associates a locale with
 * a translation representation comprised of {@link TUVContent} objects.
 * These may represent literal text, various types of inline tag, etc.
 */
public class TUV extends BaseTUVContentSink {
    private String locale;
    private List<Property> properties = new ArrayList<Property>();
    private List<Note> notes = new ArrayList<Note>();

    public TUV(String locale) {
        this.locale = locale;
        if (locale == null) {
            throw new OtterException("Can't construct a TUV without a locale");
        }
    }

    /**
     * Add an item to the contents of this TUV.  TUVs
     * are restricted to {@link TextContent}, {@link InlineTag}, and
     * {@link HighlightTag} content items.
     *
     * @param content content item to add to this object
     * @throws IllegalArgumentException if an invalid content item is added 
     */
    public TUV addContent(TUVContent content) {
        return (TUV)super.addContent(content);
    }

    /**
     * Ad multiple items to the contents of this TUV.  TUVs
     * are restricted to {@link TextContent}, {@link InlineTag}, and
     * {@link HighlightTag} content items.
     *
     * @param contents content items to add to this object
     */
    public BaseTUVContentSink addContents(List<TUVContent> contents) {
        return (TUV)super.addContents(contents);
    }

    /**
     * Get the locale string for this TUV.
     * @return locale string for this TUV
     */
    public String getLocale() {
        return locale;
    }

    /**
     * Get the properties for this TUV.  The list returned by 
     * this method is mutable.
     * @return properties for this TUV
     */
    public List<Property> getProperties() {
        return properties;
    }

    /**
     * Set the properties for this TUV.  This method will make a 
     * copy of the list passed to it.
     * @param properties new properties for this TUV
     * @return this TUV
     */
    public TUV setProperties(List<Property> properties) {
        this.properties = new ArrayList<Property>(properties);
        return this;
    }

    /**
     * Add a property to this TUV.
     * @param property new property for this TUV
     * @return this TUV
     */
    public TUV addProperty(Property property) {
        properties.add(property);
        return this;
    }

    /**
     * Get the notes for this TUV.  The list returned by this method
     * is mutable.
     * @return list of notes for this TUV
     */
    public List<Note> getNotes() {
        return notes;
    }

    /**
     * Set the notes for this TUV.  This method will make a 
     * copy of the list passed to it.
     * @param notes new list of notes for this TUV
     * @return this TUV
     */
    public TUV setNotes(List<Note> notes) {
        this.notes = new ArrayList<Note>(notes);
        return this;
    }
    
    /**
     * Add a note to this TUV.
     * @param note note to add to this TUV
     * @return this TUV 
     */
    public TUV addNote(Note note) {
        notes.add(note);
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), locale, notes, properties);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) &&
               (o instanceof TUV) &&
               locale.equals(((TUV)o).locale) && 
               notes.equals(((TUV)o).notes) &&
               properties.equals(((TUV)o).properties);
    }

    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("TUV(").append(locale).append(", ");
    	if (!notes.isEmpty()) {
    		sb.append("notes=").append(notes).append(", ");
    	}
    	if (!properties.isEmpty()) {
    		sb.append("properties=").append(properties).append(", ");
    	}
    	sb.append("content=").append(getContents());
    	sb.append(")");
    	return sb.toString();
    }
}

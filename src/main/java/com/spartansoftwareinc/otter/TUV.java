package com.spartansoftwareinc.otter;

import java.util.List;
import java.util.ArrayList;

public class TUV implements TUVContentSink {
    private String locale;
    private List<TUVContent> contents = new ArrayList<TUVContent>();
    private List<Property> properties = new ArrayList<Property>();
    private List<Note> notes = new ArrayList<Note>();
    
    public TUV(String locale) {
        this.locale = locale;
    }
    
    public String getLocale() {
        return locale;
    }
    
    public void setLocale(String locale) {
        this.locale = locale;
    }

    public List<TUVContent> getContents() {
        return contents;
    }

    public void setContents(List<TUVContent> contents) {
        this.contents = contents;
    }
    
    /**
     * Add an item to the contents of this tag.  TUV objects
     * are restricted to {@link TextContent}, {@link InlineTag}, and
     * {@link HighlightTag} content items.
     * 
     * @param content content item to add to this tag
     * @throws IllegalArgumentException if an invalid content item is added 
     */
    public void addContent(TUVContent content) {
        if (content instanceof Subflow) {
            throw new IllegalArgumentException("Subflow element not allowed in this location");
        }
        if (content instanceof CodeContent) {
            throw new IllegalArgumentException("CodeContent element not allowed in this location");
        }
        contents.add(content);
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
    
    public void addNote(Note note) {
        notes.add(note);
    }
    
    @Override
    public int hashCode() {
        return new Hasher()
            .add(locale)
            .add(contents)
            .add(notes)
            .add(properties)
            .value();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof TUV)) return false;
        TUV tuv = (TUV)o;
        return locale.equals(tuv.locale) && 
               contents.equals(tuv.contents) &&
               notes.equals(tuv.notes) &&
               properties.equals(tuv.properties);
    }

}

package com.spartansoftwareinc.otter;

import java.util.List;
import java.util.ArrayList;

public class TUV extends BaseTUVContentSink {
    private String locale;
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
        return new Hasher(super.hashCode())
            .add(locale)
            .add(notes)
            .add(properties)
            .value();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) &&
               (o instanceof TUV) &&
               locale.equals(((TUV)o).locale) && 
               notes.equals(((TUV)o).notes) &&
               properties.equals(((TUV)o).properties);
    }

}

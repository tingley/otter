package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.Util.eq;

public class TMXEvent {
    private TMXEventType eventType;
    private Object resource;
    
    protected TMXEvent(TMXEventType type) {
        this.eventType = type;
    }
    
    protected TMXEvent(TMXEventType type, Object resource) {
        this(type);
        this.resource = resource;
    }
    
    public TMXEventType getEventType() {
        return eventType;
    }
    
    public Property getProperty() {
        if (eventType == TMXEventType.HEADER_PROPERTY) {
            return (Property)resource;
        }
        throw new IllegalStateException(eventType.toString() + " event is not a property");
    }
    
    public Note getNote() {
        if (eventType == TMXEventType.HEADER_NOTE) {
            return (Note)resource;
        }
        throw new IllegalStateException(eventType.toString() + " event is not a note");
    }
    
    public Header getHeader() {
        if (eventType == TMXEventType.START_HEADER) {
            return (Header)resource;
        }
        throw new IllegalStateException(eventType.toString() + " event is not a header");
    }
    
    public TU getTU() {
        if (eventType == TMXEventType.TU) {
            return (TU)resource;
        }
        throw new IllegalStateException(eventType.toString() + " event is not a TU");
    }
    
    @Override
    public String toString() {
        return eventType.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof TMXEvent)) return false;
        TMXEvent e = (TMXEvent)o;
        return eventType.equals(e.eventType) && eq(resource, e.resource);
    }
}

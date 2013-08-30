package com.spartansoftwareinc.otter;

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
        if (eventType == TMXEventType.PROPERTY) {
            return (Property)resource;
        }
        throw new IllegalStateException(eventType.toString() + " event is not a property");
    }
    
    public Note getNote() {
        if (eventType == TMXEventType.NOTE) {
            return (Note)resource;
        }
        throw new IllegalStateException(eventType.toString() + " event is not a note");
    }
    
    public Header getHeader() {
        if (eventType == TMXEventType.START_HEADER) {
            return (Header)resource;
        }
        throw new IllegalStateException(eventType.toString() + " event is not a note");
    }
}

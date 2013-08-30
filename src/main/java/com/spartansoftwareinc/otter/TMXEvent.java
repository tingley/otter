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
}

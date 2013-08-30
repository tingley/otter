package com.spartansoftwareinc.otter;

public class TMXEvent {
    private TMXEventType eventType;
    
    protected TMXEvent(TMXEventType type) {
        this.eventType = type;
    }
    
    public TMXEventType getEventType() {
        return eventType;
    }
    
    public PropertyEvent asPropertyEvent() {
        if (eventType == TMXEventType.PROPERTY) {
            return (PropertyEvent)this;
        }
        throw new IllegalStateException(eventType.toString() + " event is not a property");
    }
}

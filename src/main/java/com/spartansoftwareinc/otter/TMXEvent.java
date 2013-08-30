package com.spartansoftwareinc.otter;

public class TMXEvent {
    private TMXEventType type;
    
    protected TMXEvent(TMXEventType type) {
        this.type = type;
    }
    
    public TMXEventType getType() {
        return type;
    }
    
}

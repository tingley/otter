package com.spartansoftwareinc.otter;

public class PropertyEvent extends TMXEvent {

    public PropertyEvent() {
        super(TMXEventType.PROPERTY);
    }
    
    public String type, value;
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public PropertyEvent(String type, String value) {
        this();
        this.type = type;
        this.value = value;
    }
    
}

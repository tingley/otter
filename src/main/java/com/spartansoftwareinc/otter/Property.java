package com.spartansoftwareinc.otter;

public class Property {

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

    public Property(String type, String value) {
        this.type = type;
        this.value = value;
    }
    
}

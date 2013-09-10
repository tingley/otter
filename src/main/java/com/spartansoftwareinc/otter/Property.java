package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.Util.eq;

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

    @Override
    public int hashCode() {
        return new Hasher()
            .add(type)
            .add(value)
            .value();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof Property)) return false;
        Property p = (Property)o;
        return eq(type, p.type) && eq(value, p.value);
    }
}

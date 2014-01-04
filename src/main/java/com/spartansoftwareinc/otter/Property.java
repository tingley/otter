package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.Util.eq;

/**
 * Representation of a <code>&lt;prop&gt;</code> element.
 * Properties can be attached to the header or to a {link TU}.
 */
public class Property {
    private String type, value;
    
    public Property(String type, String value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Get the value of the <code>type</code> attribute for this
     * property.
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * Set the type of this property.
     * @param type
     * @return
     */
    public Property setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get the value of this property.
     * @return
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the value of this property.
     * @param value
     */
    public Property setValue(String value) {
        this.value = value;
        return this;
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

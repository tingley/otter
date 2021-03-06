package net.sundell.otter;

import java.util.Objects;

/**
 * Representation of a <code>&lt;prop&gt;</code> element.
 * Properties can be attached to the header or to a {link TU}.
 */
public class Property {
    private String type, value;
    private String encoding, lang;
    
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
     * @return this Property instance
     */
    public Property setType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Get the value of this property.
     * @return property value
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the value of this property.
     * @param value new property value
     * @return this Property instance
     */
    public Property setValue(String value) {
        this.value = value;
        return this;
    }
    
    /**
     * Get the value of the <code>o-encoding</code> attribute
     * for this property, if present.
     * @return encoding value 
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Set the value of the <code>o-encoding</code> attribute for
     * this property.  No validation is performed.
     * @param encoding encoding for this property
     * @return this Property instance
     */
    public Property setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }
    
    /**
     * Get the value of the <code>xml:lang</code> attribute
     * for this property, if present.
     * @return xml:lang value
     */
    public String getLang() {
        return lang;
    }

    /**
     * Set the value of the <code>xml:lang</code> attribute for
     * this property.  No validation is performed.
     * @param lang xml:lang value for this property
     * @return property object
     */
    public Property setLang(String lang) {
        this.lang = lang;
        return this;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value, encoding, lang);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof Property)) return false;
        Property p = (Property)o;
        return Objects.equals(type, p.type) && Objects.equals(value, p.value) &&
               Objects.equals(encoding, p.encoding) && Objects.equals(lang, p.lang);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Property(type='" + type + "' value='" + value + "'");
        if (encoding != null) {
            sb.append(" o-encoding='" + encoding + "'");
        }
        if (lang != null) {
            sb.append(" xml:lang='" + lang + "'");
        }
        sb.append(")");
        return sb.toString();
    }
}

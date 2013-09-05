package com.spartansoftwareinc.otter;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;

class Util {
    static void require(boolean condition, Location location, String message) {
        if (!condition) {
            throw new OtterException(message, location);
        }
    }
    static String attrVal(StartElement el, QName attrName) {
        Attribute a = el.getAttributeByName(attrName);
        return (a == null) ? null : a.getValue();
    }
    static String requireAttrVal(StartElement el, QName attrName) {
        Attribute a = el.getAttributeByName(attrName);
        if (a == null) {
            throw new OtterException("Required attribute " + attrName + " is missing", 
                    el.getLocation());
        }
        return a.getValue();
    }
    static Integer attrValAsInteger(StartElement el, QName attrName) {
        Attribute a = el.getAttributeByName(attrName);
        if (a == null) return null;
        try {
            return Integer.valueOf(a.getValue());
        }
        catch (NumberFormatException e) {
            throw new OtterException("Not an integer value: " + a.getValue(),
                                     el.getLocation());
        }
    }
    static Integer requireAttrValAsInteger(StartElement el, QName attrName) {
        Attribute a = el.getAttributeByName(attrName);
        if (a == null) {
            throw new OtterException("Required attribute " + attrName + " is missing", 
                    el.getLocation());
        }
        try {
            return Integer.valueOf(a.getValue());
        }
        catch (NumberFormatException e) {
            throw new OtterException("Not an integer value: " + a.getValue(),
                                     el.getLocation());
        }
    }
    static boolean eq(Object o1, Object o2) {
        if (o1 == null && o2 == null) return true;
        if (o1 != null && o2 != null) return o1.equals(o2);
        return false;
    }
}

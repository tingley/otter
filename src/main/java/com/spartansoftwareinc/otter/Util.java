package com.spartansoftwareinc.otter;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.StartElement;

class Util {
    static Reader stripBOM(Reader r) throws IOException {
        PushbackReader pushback = new PushbackReader(r, 1);
        int bom = pushback.read();
        if (bom != -1 && bom != '\uFEFF') {
            pushback.unread(bom);
        }
        return pushback;
    }
    static void require(boolean condition, Location location, String message) {
        if (!condition) {
            throw new OtterInputException(message, location);
        }
    }
    static String attrVal(StartElement el, QName attrName) {
        Attribute a = el.getAttributeByName(attrName);
        return (a == null) ? null : a.getValue();
    }
    static String requireAttrVal(StartElement el, QName attrName, ErrorHandler handler) {
        Attribute a = el.getAttributeByName(attrName);
        if (a == null) {
            handler.fatalError(
                    new OtterInputException("Required attribute " + attrName + " is missing", 
                            el.getLocation()));
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
            throw new OtterInputException("Not an integer value: " + a.getValue(),
                                     el.getLocation());
        }
    }
    // 20100223T044327Z
    static final String TMX_DATE_FORMAT = "yyyyMMdd'T'HHmmss'Z'";
    static final Date parseTMXDate(String s) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(TMX_DATE_FORMAT);
            return df.parse(s);
        }
        catch (ParseException e) {
            return null;
        }
    }
    static final String writeTMXDate(Date d) {
        return new SimpleDateFormat(TMX_DATE_FORMAT).format(d);
    }
    /**
     * If an ErrorHandler is specified, this will report an error via the error() method.
     * Otherwise, the error is reported as an OtterException.
     */
    static Date attrValAsDate(StartElement el, QName attrName, ErrorHandler handler) throws OtterException {
        Attribute a = el.getAttributeByName(attrName);
        if (a == null) return null;
        Date d = parseTMXDate(a.getValue());
        if (d == null) {
            OtterInputException e = new OtterInputException("Invalid date format '" + 
                    a.getValue() + "' for " + attrName.getLocalPart(),
                    el.getLocation());
            if (handler != null) {
                handler.error(e);
            }
            else {
                throw e;
            }
        }
        return d;
    }
    static boolean eq(Object o1, Object o2) {
        if (o1 == null && o2 == null) return true;
        if (o1 != null && o2 != null) return o1.equals(o2);
        return false;
    }
}

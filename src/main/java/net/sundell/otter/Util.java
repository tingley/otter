package net.sundell.otter;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
            return null;
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
    /**
     * If an ErrorHandler is specified, this will report an error via the error() method.
     * Otherwise, the error is reported as an OtterException.
     */
    static Date attrValAsDate(StartElement el, QName attrName, TMXDateParser dateParser,
                              ErrorHandler handler) throws OtterException {
        Attribute a = el.getAttributeByName(attrName);
        if (a == null) return null;
        Date d = dateParser.parseDate(a.getValue());
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

    static List<TUVContent> normalizeWhitespace(List<TUVContent> orig) {
        List<TUVContent> normalized = new ArrayList<>(orig.size());
        for (int i = 0; i < orig.size(); i++) {
            TUVContent content = orig.get(i);
            if (!(content instanceof TextContent)) {
                normalized.add(content);
                continue;
            }

            String text = ((TextContent)content).getValue();
            normalized.add(new TextContent(normalizeWhitespace(text, i == 0, i + 1 == orig.size())));
        }
        return normalized;
    }

    // Horrendous
    private static String normalizeWhitespace(String text, boolean trimLeading, boolean trimTrailing) {
        StringBuilder sb = new StringBuilder();
        int spaceCount = 0;
        char[] raw = text.toCharArray();
        int leadingSpaceCount = trimLeading ? text.indexOf(text.trim()) : 0;
        for (int i = leadingSpaceCount; i < raw.length; i++) {
            char c = raw[i];
            if (!(Character.isWhitespace(c))) {
                sb.append(c);
                spaceCount = 0;
                continue;
            }
            if (spaceCount == 0) {
                sb.append(' ');
            }
            spaceCount++;
        }
        String temp = sb.toString();
        if (trimTrailing) {
            int end = temp.length();
            while (end > 0 && Character.isWhitespace(temp.charAt(end - 1))) {
                end--;
            }
            temp = temp.substring(0, end);
        }
        return temp;
    }
}

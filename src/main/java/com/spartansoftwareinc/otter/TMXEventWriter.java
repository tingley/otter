package com.spartansoftwareinc.otter;

import java.io.Writer;

import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;

public class TMXEventWriter {

    public static TMXEventWriter createTMXEventWriter(Writer w) 
                throws XMLStreamException {
        return new TMXEventWriter(w);
    }

    private XMLEventWriter xmlWriter;
    
    private TMXEventWriter(Writer w) throws XMLStreamException {
        XMLOutputFactory factory = XMLOutputFactory.newFactory();
        xmlWriter = factory.createXMLEventWriter(w);
    }
    
    @Deprecated
    private void TODO() {
        throw new UnsupportedOperationException();
    }
    
    public void startTMX() {
        TODO();
    }
    
    public void endTMX() {
        TODO();
    }
    
    public void startHeader(Header h) {
        TODO();
    }
    
    public void endHeader() {
        TODO();
    }
    
    public void writeProperty(String name, String value) {
        TODO();
    }
    
    public void writeNote(String value) {
        TODO();
    }
    
    public void startBody() {
        TODO();
    }
    
    public void endBody() {
        TODO();
    }
}

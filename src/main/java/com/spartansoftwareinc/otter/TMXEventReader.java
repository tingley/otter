package com.spartansoftwareinc.otter;

import java.io.Reader;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

public class TMXEventReader {
    
    public static TMXEventReader createTMXEventReader(Reader r) 
                throws XMLStreamException {
        return new TMXEventReader(r);
    }

    private TMXEventReader(Reader r) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        this.reader = factory.createXMLEventReader(r);
    }
    
    private XMLEventReader reader;

    public boolean hasNext() {
        throw new UnsupportedOperationException();
    }
    
    public TMXEvent nextEvent() {
        throw new UnsupportedOperationException();
    }
}

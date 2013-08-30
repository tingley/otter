package com.spartansoftwareinc.otter;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

import net.sundell.snax.DefaultElementHandler;
import net.sundell.snax.NodeModel;
import net.sundell.snax.NodeModelBuilder;
import net.sundell.snax.SNAXParser;
import net.sundell.snax.SNAXUserException;

import static com.spartansoftwareinc.otter.TMXEventType.*;

public class TMXEventReader {
    
    public static TMXEventReader createTMXEventReader(Reader r) 
                throws XMLStreamException {
        return new TMXEventReader(r);
    }

    private TMXEventReader(Reader r) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        parser = SNAXParser.createParser(factory, buildModel());
        parser.startParsing(r, new TMXEventSink());
    }
    
    private SNAXParser<TMXEventSink> parser;
    private List<TMXEvent> events = new ArrayList<TMXEvent>();

    public boolean hasNext() throws XMLStreamException {
        if (events.size() > 0) {
            return true;
        }
        while (parser.hasMoreEvents()) {
            parser.processEvent();
            if (events.size() > 0) {
                return true;
            }
        }
        return false;
    }
    
    public TMXEvent nextEvent() {
        if (events.size() > 0) {
            return events.remove(0);
        }
        throw new IllegalStateException("No event available");
    }
    
    class TMXEventSink {
        
    }
    
    protected void addEvent(TMXEvent event) {
        events.add(event);
    }
    
    private NodeModel<TMXEventSink> buildModel() {
        return new NodeModelBuilder<TMXEventSink>() {{
            elements("tmx").attach(new TMXHandler());
            elements("tmx", "header").attach(new HeaderHandler());
            elements("tmx", "header", "prop").attach(new PropertyHandler());
            elements("tmx", "header", "note").attach(new NoteHandler());
            elements("tmx", "body").attach(new BodyHandler());
        }}.build();
    }
        
    static final QName TYPE = new QName("type");

    class TMXHandler extends DefaultElementHandler<TMXEventSink> {
        @Override
        public void startElement(StartElement element, TMXEventSink data)
                throws SNAXUserException {
            // TODO verify version
            addEvent(new TMXEvent(START_TMX));
        }
        @Override
        public void endElement(EndElement element, TMXEventSink data)
                throws SNAXUserException {
            addEvent(new TMXEvent(END_TMX));
        }
    }
    class HeaderHandler extends DefaultElementHandler<TMXEventSink> {
        @Override
        public void startElement(StartElement element, TMXEventSink data)
                throws SNAXUserException {
            // TODO Handle attributes
            addEvent(new TMXEvent(START_HEADER));
        }
        @Override
        public void endElement(EndElement element, TMXEventSink data)
                throws SNAXUserException {
            addEvent(new TMXEvent(END_HEADER));
        }
    }
    class PropertyHandler extends DefaultElementHandler<TMXEventSink> {
        private StringBuilder value = new StringBuilder();
        private String type = null;
        @Override
        public void startElement(StartElement element, TMXEventSink data)
                throws SNAXUserException {
            value.setLength(0);
            type = attrVal(element, TYPE);
        }
        @Override
        public void characters(StartElement parent, Characters characters,
                TMXEventSink data) throws SNAXUserException {
            value.append(characters.getData());
        }
        @Override
        public void endElement(EndElement element, TMXEventSink data)
                throws SNAXUserException {
            require(type != null, element.getLocation(), "Property type was not set");
            addEvent(new TMXEvent(PROPERTY, new Property(type, value.toString())));
        }
    }
    class NoteHandler extends DefaultElementHandler<TMXEventSink> {
        @Override
        public void startElement(StartElement element, TMXEventSink data)
                throws SNAXUserException {
            // TODO Handle attributes, start buffering
        }
        @Override
        public void characters(StartElement parent, Characters characters,
                TMXEventSink data) throws SNAXUserException {
            // TODO handle
        }
        @Override
        public void endElement(EndElement element, TMXEventSink data)
                throws SNAXUserException {
            addEvent(new TMXEvent(NOTE));
        }
    }
    class BodyHandler extends DefaultElementHandler<TMXEventSink> {
        @Override
        public void startElement(StartElement element, TMXEventSink data)
                throws SNAXUserException {
            addEvent(new TMXEvent(START_BODY));
        }
        @Override
        public void endElement(EndElement element, TMXEventSink data)
                throws SNAXUserException {
            addEvent(new TMXEvent(END_BODY));
        }
    }
    
    private void require(boolean condition, Location location, String message) {
        if (!condition) {
            throw new OtterException(message, location);
        }
    }
    private String attrVal(StartElement el, QName attrName) {
        Attribute a = el.getAttributeByName(attrName);
        return (a == null) ? null : a.getValue();
    }
}

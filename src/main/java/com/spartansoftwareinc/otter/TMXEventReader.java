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
            elements("tmx", "header", "prop").attach(new HeaderPropertyHandler());
            elements("tmx", "header", "note").attach(new HeaderNoteHandler());
            elements("tmx", "body").attach(new BodyHandler());
        }}.build();
    }
        
    static final QName TYPE = new QName("type");
    static final QName CREATIONTOOL = new QName("creationtool");
    static final QName CREATIONTOOLVERSION = new QName("creationtoolversion");
    static final QName SEGTYPE = new QName("segtype");
    static final QName TMF = new QName("o-tmf");
    static final QName ADMINLANG = new QName("adminlang");
    static final QName SRCLANG = new QName("srclang");
    static final QName DATATYPE = new QName("datatype");
    static final QName ENCODING = new QName("o-encoding");
    static final QName CREATIONDATE = new QName("creationdate");
    static final QName CREATIONID = new QName("creationid");
    static final QName CHANGEDATE = new QName("changedate");
    static final QName CHANGEID = new QName("changeid");

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
            Header header = new Header();
            header.setAdminLang(attrVal(element, ADMINLANG));
            header.setChangeDate(attrVal(element, CHANGEDATE));
            header.setChangeId(attrVal(element, CHANGEID));
            header.setCreationDate(attrVal(element, CREATIONDATE));
            header.setCreationId(attrVal(element, CREATIONID));
            header.setCreationTool(attrVal(element, CREATIONTOOL));
            header.setCreationToolVersion(attrVal(element, CREATIONTOOLVERSION));
            header.setDataType(attrVal(element, DATATYPE));
            header.setEncoding(attrVal(element, ENCODING));
            header.setSegType(attrVal(element, SEGTYPE));
            header.setSrcLang(attrVal(element, SRCLANG));
            header.setTmf(attrVal(element, TMF));
            addEvent(new TMXEvent(START_HEADER, header));
        }
        @Override
        public void endElement(EndElement element, TMXEventSink data)
                throws SNAXUserException {
            addEvent(new TMXEvent(END_HEADER));
        }
    }
    class HeaderPropertyHandler extends DefaultElementHandler<TMXEventSink> {
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
            addEvent(new TMXEvent(HEADER_PROPERTY, new Property(type, value.toString())));
        }
    }
    class HeaderNoteHandler extends DefaultElementHandler<TMXEventSink> {
        private StringBuilder value = new StringBuilder();
        @Override
        public void startElement(StartElement element, TMXEventSink data)
                throws SNAXUserException {
            value.setLength(0);
        }
        @Override
        public void characters(StartElement parent, Characters characters,
                TMXEventSink data) throws SNAXUserException {
            value.append(characters.getData());
        }
        @Override
        public void endElement(EndElement element, TMXEventSink data)
                throws SNAXUserException {
            addEvent(new TMXEvent(HEADER_NOTE, new Note(value.toString())));
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

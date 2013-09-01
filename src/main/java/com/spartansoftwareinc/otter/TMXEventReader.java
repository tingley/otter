package com.spartansoftwareinc.otter;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

import net.sundell.snax.DefaultElementHandler;
import net.sundell.snax.NodeModel;
import net.sundell.snax.NodeModelBuilder;
import net.sundell.snax.SNAXParser;
import net.sundell.snax.SNAXUserException;

import static com.spartansoftwareinc.otter.TMXEventType.*;
import static com.spartansoftwareinc.otter.Util.*;

public class TMXEventReader {
    
    public static TMXEventReader createTMXEventReader(Reader r) 
                throws XMLStreamException {
        return new TMXEventReader(r);
    }

    private TMXEventReader(Reader r) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        parser = SNAXParser.createParser(factory, buildModel());
        parser.startParsing(r, new SegmentBuilder());
    }
    
    private SNAXParser<SegmentBuilder> parser;
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
    
    protected void addEvent(TMXEvent event) {
        events.add(event);
    }
    
    private NodeModel<SegmentBuilder> buildModel() {
        return new NodeModelBuilder<SegmentBuilder>() {{
            elements("tmx").attach(new TMXHandler());
            elements("tmx", "header").attach(new HeaderHandler());
            elements("tmx", "header", "prop").attach(new HeaderPropertyHandler());
            elements("tmx", "header", "note").attach(new HeaderNoteHandler());
            elements("tmx", "body").attach(new BodyHandler());
            elements("tmx", "body", "tu").attach(new TuHandler());
            elements("tmx", "body", "tu", "tuv").attach(new TuvHandler());
            elements("tmx", "body", "tu", "tuv", "seg").attach(new SegHandler());
            elements("tmx", "body", "tu", "tuv", "seg", "ph").attach(new SegPhHandler());
        }}.build();
    }
    
    static final QName X = new QName("x");
    static final QName TYPE = new QName("type");
    static final QName ASSOC = new QName("assoc");
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

    class TMXHandler extends DefaultElementHandler<SegmentBuilder> {
        @Override
        public void startElement(StartElement element, SegmentBuilder data)
                throws SNAXUserException {
            // TODO verify version
            addEvent(new TMXEvent(START_TMX));
        }
        @Override
        public void endElement(EndElement element, SegmentBuilder data)
                throws SNAXUserException {
            addEvent(new TMXEvent(END_TMX));
        }
    }
    class HeaderHandler extends DefaultElementHandler<SegmentBuilder> {
        @Override
        public void startElement(StartElement element, SegmentBuilder data)
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
        public void endElement(EndElement element, SegmentBuilder data)
                throws SNAXUserException {
            addEvent(new TMXEvent(END_HEADER));
        }
    }
    class HeaderPropertyHandler extends DefaultElementHandler<SegmentBuilder> {
        private StringBuilder value = new StringBuilder();
        private String type = null;
        @Override
        public void startElement(StartElement element, SegmentBuilder data)
                throws SNAXUserException {
            value.setLength(0);
            type = attrVal(element, TYPE);
        }
        @Override
        public void characters(StartElement parent, Characters characters,
                SegmentBuilder data) throws SNAXUserException {
            value.append(characters.getData());
        }
        @Override
        public void endElement(EndElement element, SegmentBuilder data)
                throws SNAXUserException {
            require(type != null, element.getLocation(), "Property type was not set");
            addEvent(new TMXEvent(HEADER_PROPERTY, new Property(type, value.toString())));
        }
    }
    class HeaderNoteHandler extends DefaultElementHandler<SegmentBuilder> {
        private StringBuilder value = new StringBuilder();
        @Override
        public void startElement(StartElement element, SegmentBuilder data)
                throws SNAXUserException {
            value.setLength(0);
        }
        @Override
        public void characters(StartElement parent, Characters characters,
                SegmentBuilder data) throws SNAXUserException {
            value.append(characters.getData());
        }
        @Override
        public void endElement(EndElement element, SegmentBuilder data)
                throws SNAXUserException {
            addEvent(new TMXEvent(HEADER_NOTE, new Note(value.toString())));
        }
    }
    class BodyHandler extends DefaultElementHandler<SegmentBuilder> {
        @Override
        public void startElement(StartElement element, SegmentBuilder data)
                throws SNAXUserException {
            addEvent(new TMXEvent(START_BODY));
        }
        @Override
        public void endElement(EndElement element, SegmentBuilder data)
                throws SNAXUserException {
            addEvent(new TMXEvent(END_BODY));
        }
    }
    class TuHandler extends DefaultElementHandler<SegmentBuilder> {
        @Override
        public void startElement(StartElement element, SegmentBuilder data)
                throws SNAXUserException {
            data.startTu(element);
        }
        @Override
        public void endElement(EndElement element, SegmentBuilder data)
                throws SNAXUserException {
            addEvent(new TMXEvent(TU, data.getTu()));
        }
    }
    class TuvHandler extends DefaultElementHandler<SegmentBuilder> {
        @Override
        public void startElement(StartElement element, SegmentBuilder data)
                throws SNAXUserException {
            data.startTuv(element);
        }
        @Override
        public void endElement(EndElement element, SegmentBuilder data)
                throws SNAXUserException {
            data.endTuv();
        }
    }
    class SegHandler extends DefaultElementHandler<SegmentBuilder> {
        // Buffer goes in the sink so I can handle inline codes
        @Override
        public void characters(StartElement parent, Characters characters,
                SegmentBuilder data) throws SNAXUserException {
            data.addSegmentText(characters.getData());
        }
    }
    class SegPhHandler extends DefaultElementHandler<SegmentBuilder> {
        private StringBuilder sb = new StringBuilder();
        private Integer x;
        private String type, assoc;
        @Override
        public void startElement(StartElement element, SegmentBuilder data)
                throws SNAXUserException {
            sb.setLength(0);
            x = attrValAsInteger(element, X);
            type = attrVal(element, TYPE);
            assoc = attrVal(element, ASSOC);
        }
        @Override
        public void characters(StartElement parent, Characters characters,
                SegmentBuilder data) throws SNAXUserException {
            sb.append(characters.getData());
        }
        @Override
        public void endElement(EndElement element, SegmentBuilder data)
                throws SNAXUserException {
            PhTag ph = new PhTag(sb.toString());
            if (x != null) {
                ph.setX(x);
            }
            if (type != null) {
                ph.setType(type);
            }
            if (assoc != null) {
                ph.setType(assoc);
            }
            data.addTUVContent(ph);
        }
    }

}

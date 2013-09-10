package com.spartansoftwareinc.otter;

import java.io.Reader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

import net.sundell.snax.AttachPoint;
import net.sundell.snax.DefaultElementHandler;
import net.sundell.snax.NodeModel;
import net.sundell.snax.NodeModelBuilder;
import net.sundell.snax.SNAXParser;
import net.sundell.snax.SNAXUserException;

import static com.spartansoftwareinc.otter.TMXConstants.*;
import static com.spartansoftwareinc.otter.TMXEventType.*;
import static com.spartansoftwareinc.otter.Util.*;

public class TMXEventReader {
    
    public static TMXEventReader createTMXEventReader(Reader r) 
                throws XMLStreamException {
        return new TMXEventReader(r);
    }

    private TMXEventReader(Reader r) throws XMLStreamException {
        XMLInputFactory factory = XMLInputFactory.newFactory();
        factory.setProperty(XMLInputFactory.IS_COALESCING, true);
        parser = SNAXParser.createParser(factory, buildModel());
        parser.startParsing(r, new SegmentBuilder());
    }
    
    private SNAXParser<SegmentBuilder> parser;
    private List<TMXEvent> events = new ArrayList<TMXEvent>();
    private Deque<TUVContentSink> contentStack = new ArrayDeque<TUVContentSink>();
    
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
    
    protected void addTUVContent(TUVContent content) {
        contentStack.peek().addContent(content);
    }
    protected void addTextContent(String text) {
        addTUVContent(new TextContent(text));
    }
    protected void addCodeContent(String codes) {
        addTUVContent(new CodeContent(codes));
    }
    
    private NodeModel<SegmentBuilder> buildModel() {
        return new NodeModelBuilder<SegmentBuilder>() {
            AttachPoint<SegmentBuilder> phAnchor, bptAnchor, eptAnchor, itAnchor;
            {
            elements("tmx").attach(new TMXHandler());
            elements("tmx", "header").attach(new HeaderHandler());
            elements("tmx", "header", "prop").attach(new HeaderPropertyHandler());
            elements("tmx", "header", "note").attach(new HeaderNoteHandler());
            elements("tmx", "body").attach(new BodyHandler());
            elements("tmx", "body", "tu").attach(new TuHandler());
            elements("tmx", "body", "tu", "tuv").attach(new TuvHandler());
            elements("tmx", "body", "tu", "tuv", "seg").attach(new SegHandler());
            elements("tmx", "body", "tu", "tuv", "prop").attach(new TuvPropertyHandler());
            elements("tmx", "body", "tu", "tuv", "note").attach(new TuvNoteHandler());
            
            phAnchor = elements("tmx", "body", "tu", "tuv", "seg", "ph").attachPoint();
            bptAnchor = elements("tmx", "body", "tu", "tuv", "seg", "bpt").attachPoint();
            eptAnchor = elements("tmx", "body", "tu", "tuv", "seg", "ept").attachPoint();
            itAnchor = elements("tmx", "body", "tu", "tuv", "seg", "it").attachPoint();

            elements("tmx", "body", "tu", "tuv", "seg", "ph").attach(new SegPhHandler());
            elements("tmx", "body", "tu", "tuv", "seg", "bpt").attach(new SegBptHandler());
            elements("tmx", "body", "tu", "tuv", "seg", "ept").attach(new SegEptHandler());
            elements("tmx", "body", "tu", "tuv", "seg", "it").attach(new SegItHandler());

            // Exhaustively link each type of subflow back to the correct anchor
            elements("tmx", "body", "tu", "tuv", "seg", "ph", "sub").attach(new SegSubHandler());
            attachSubflowStates("ph");
            elements("tmx", "body", "tu", "tuv", "seg", "bpt", "sub").attach(new SegSubHandler());
            attachSubflowStates("bpt");
            elements("tmx", "body", "tu", "tuv", "seg", "ept", "sub").attach(new SegSubHandler());
            attachSubflowStates("ept");
            elements("tmx", "body", "tu", "tuv", "seg", "it", "sub").attach(new SegSubHandler());
            attachSubflowStates("it");
            
            // Snax currently doesn't support anything like conditional handlers based
            // on which incoming transition to a state was used.  Rather than add them,
            // I'm going to make the state machine larger, so that the <hi>-descendant
            // state loop is separate from the overall content state loop.
            AttachPoint<SegmentBuilder> hiAnchor = elements("tmx", "body", "tu", "tuv", "seg", "hi").attachPoint();
            elements("tmx", "body", "tu", "tuv", "seg", "hi").attach(new SegHiHandler());
            elements("tmx", "body", "tu", "tuv", "seg", "hi").addTransition("hi", hiAnchor); // Nested case
            elements("tmx", "body", "tu", "tuv", "seg", "hi", "ph").attach(new SegPhHandler());
            elements("tmx", "body", "tu", "tuv", "seg", "hi", "bpt").attach(new SegBptHandler());
            elements("tmx", "body", "tu", "tuv", "seg", "hi", "ept").attach(new SegEptHandler());
            elements("tmx", "body", "tu", "tuv", "seg", "hi", "it").attach(new SegItHandler());

        }
        private void attachSubflowStates(String tagName) {
            elements("tmx", "body", "tu", "tuv", "seg", tagName, "sub").addTransition("ph", phAnchor);
            elements("tmx", "body", "tu", "tuv", "seg", tagName, "sub").addTransition("bpt", bptAnchor);
            elements("tmx", "body", "tu", "tuv", "seg", tagName, "sub").addTransition("ept", eptAnchor);
            elements("tmx", "body", "tu", "tuv", "seg", tagName, "sub").addTransition("it", itAnchor);
        }
        }.build();
    }
    
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
    abstract class PropertyHandler extends DefaultElementHandler<SegmentBuilder> {
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
            handleProperty(data, new Property(type, value.toString()));
        }
        abstract void handleProperty(SegmentBuilder data, Property property);
    }
    class HeaderPropertyHandler extends PropertyHandler {
        @Override
        void handleProperty(SegmentBuilder data, Property property) {
            addEvent(new TMXEvent(HEADER_PROPERTY, property));
        }
    }
    abstract class NoteHandler extends DefaultElementHandler<SegmentBuilder> {
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
            handleNote(data, new Note(value.toString()));
        }
        abstract void handleNote(SegmentBuilder data, Note note);
    }
    class HeaderNoteHandler extends NoteHandler {
        @Override
        void handleNote(SegmentBuilder data, Note note) {
            addEvent(new TMXEvent(HEADER_NOTE, note));
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
            addEvent(new TMXEvent(TMXEventType.TU, data.getTu()));
        }
    }
    class TuvHandler extends DefaultElementHandler<SegmentBuilder> {
        @Override
        public void startElement(StartElement element, SegmentBuilder data)
                throws SNAXUserException {
            data.startTuv(element);
            contentStack.push(data.getCurrentTuv());
        }
        @Override
        public void endElement(EndElement element, SegmentBuilder data)
                throws SNAXUserException {
            data.endTuv();
            contentStack.pop();
        }
    }
    class TuvPropertyHandler extends PropertyHandler {
        @Override
        void handleProperty(SegmentBuilder data, Property property) {
            data.getCurrentTuv().addProperty(property);
        }
    }
    class TuvNoteHandler extends NoteHandler {
        @Override
        void handleNote(SegmentBuilder data, Note note) {
            data.getCurrentTuv().addNote(note);
        }
    }
    class SegHandler extends DefaultElementHandler<SegmentBuilder> {
        @Override
        public void characters(StartElement parent, Characters characters,
                SegmentBuilder data) throws SNAXUserException {
            addTextContent(characters.getData());
        }
    }
    class SegPhHandler extends DefaultElementHandler<SegmentBuilder> {
        @Override
        public void startElement(StartElement element, SegmentBuilder data)
                throws SNAXUserException {
            PhTag ph = new PhTag();
            Integer x = attrValAsInteger(element, X);
            if (x != null) {
                ph.setX(x);
            }
            ph.setType(attrVal(element, TYPE));
            ph.setAssoc(attrVal(element, ASSOC));
            addTUVContent(ph);
            contentStack.push(ph);
        }
        @Override
        public void characters(StartElement parent, Characters characters,
                SegmentBuilder data) throws SNAXUserException {
            addCodeContent(characters.getData());
        }
        @Override
        public void endElement(EndElement element, SegmentBuilder data)
                throws SNAXUserException {
            contentStack.pop();
        }
    }
    class SegBptHandler extends DefaultElementHandler<SegmentBuilder> {
        @Override
        public void startElement(StartElement element, SegmentBuilder data)
                throws SNAXUserException {
            BptTag bpt = new BptTag(requireAttrValAsInteger(element, I));
            Integer x = attrValAsInteger(element, X);
            if (x != null) {
                bpt.setX(x);
            }
            String type = attrVal(element, TYPE);
            if (type != null) {
                bpt.setType(type);
            }
            addTUVContent(bpt);
            contentStack.push(bpt);
        }
        @Override
        public void characters(StartElement parent, Characters characters,
                SegmentBuilder data) throws SNAXUserException {
            addCodeContent(characters.getData());
        }
        @Override
        public void endElement(EndElement element, SegmentBuilder data)
                throws SNAXUserException {
            contentStack.pop();
        }
    }
    class SegEptHandler extends DefaultElementHandler<SegmentBuilder> {
        @Override
        public void startElement(StartElement element, SegmentBuilder data)
                throws SNAXUserException {
            EptTag ept = new EptTag(requireAttrValAsInteger(element, I));
            addTUVContent(ept);
            contentStack.push(ept);
        }
        @Override
        public void characters(StartElement parent, Characters characters,
                SegmentBuilder data) throws SNAXUserException {
            addCodeContent(characters.getData());
        }
        @Override
        public void endElement(EndElement element, SegmentBuilder data)
                throws SNAXUserException {
            contentStack.pop();
        }
    }
    class SegItHandler extends DefaultElementHandler<SegmentBuilder> {
        @Override
        public void startElement(StartElement element, SegmentBuilder data)
                throws SNAXUserException {
            ItTag it = new ItTag();
            Integer x = attrValAsInteger(element, X);
            if (x != null) {
                it.setX(x);
            }
            it.setType(attrVal(element, TYPE));
            String v = requireAttrVal(element, POS);
            ItTag.Pos pos = ItTag.Pos.byAttrValue(v);
            if (pos == null) {
                throw new OtterException("Invalid value for 'pos' attribute: " + v, 
                        element.getLocation());
            }
            it.setPos(pos);
            addTUVContent(it);
            contentStack.push(it);
        }
        @Override
        public void characters(StartElement parent, Characters characters,
                SegmentBuilder data) throws SNAXUserException {
            addCodeContent(characters.getData());
        }
        @Override
        public void endElement(EndElement element, SegmentBuilder data)
                throws SNAXUserException {
            contentStack.pop();
        }
    }
    class SegHiHandler extends DefaultElementHandler<SegmentBuilder> {
        @Override
        public void startElement(StartElement element, SegmentBuilder data)
                throws SNAXUserException {
            HiTag hi = new HiTag();
            Integer x = attrValAsInteger(element, X);
            if (x != null) {
                hi.setX(x);
            }
            String type = attrVal(element, TYPE);
            if (type != null) {
                hi.setType(type);
            }
            // Add it to the current container, then push it onto 
            // the stack to catch content up to </hi>
            addTUVContent(hi);
            contentStack.push(hi);
        }
        @Override
        public void characters(StartElement parent, Characters characters,
                SegmentBuilder data) throws SNAXUserException {
            addTextContent(characters.getData());
        }
        @Override
        public void endElement(EndElement element, SegmentBuilder data)
                throws SNAXUserException {
            contentStack.pop();
        }
    }
    class SegSubHandler extends DefaultElementHandler<SegmentBuilder> {
        @Override
        public void startElement(StartElement element, SegmentBuilder data)
                throws SNAXUserException {
            Subflow subflow = new Subflow();
            String type = attrVal(element, TYPE);
            if (type != null) {
                subflow.setType(type);
            }
            String dataType = attrVal(element, DATATYPE);
            if (dataType != null) {
                subflow.setDatatype(dataType);
            }
            addTUVContent(subflow);
            contentStack.push(subflow);
        }
        @Override
        public void characters(StartElement parent, Characters contents,
                SegmentBuilder data) throws SNAXUserException {
            addTextContent(contents.getData());
        }
        @Override
        public void endElement(EndElement element, SegmentBuilder data)
                throws SNAXUserException {
            contentStack.pop();
        }
    }
}

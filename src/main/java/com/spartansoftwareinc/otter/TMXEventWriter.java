package com.spartansoftwareinc.otter;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;

import static com.spartansoftwareinc.otter.TMXConstants.*;

public class TMXEventWriter {

    public static TMXEventWriter createTMXEventWriter(Writer w) 
                throws XMLStreamException {
        return new TMXEventWriter(w);
    }

    private XMLEventWriter xmlWriter;
    private XMLEventFactory eventFactory;
    
    private TMXEventWriter(Writer w) throws XMLStreamException {
        XMLOutputFactory factory = XMLOutputFactory.newFactory();
        xmlWriter = factory.createXMLEventWriter(w);
        eventFactory = XMLEventFactory.newInstance();
    }
    
    @Deprecated
    private void TODO() {
        throw new UnsupportedOperationException();
    }
    
    public void writeEvent(TMXEvent event) throws XMLStreamException {
        switch (event.getEventType()) {
        case START_TMX:
            startTMX();
            break;
        case END_TMX:
            endTMX();
            break;
        case START_HEADER:
            startHeader(event.getHeader());
            break;
        case END_HEADER:
            endHeader();
            break;
        case START_BODY:
            startBody();
            break;
        case END_BODY:
            endBody();
            break;
        case HEADER_PROPERTY:
            Property p = event.getProperty();
            writeProperty(p.type, p.value);
            break;
        case HEADER_NOTE:
            Note n = event.getNote();
            writeNote(n.getContent());
            break;
        case TU:
            writeTu(event.getTU());
            break;
        default:
            TODO();
            break;
        }
    }
    
    public void startTMX() throws XMLStreamException {
        xmlWriter.add(eventFactory.createStartDocument());
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        attrs.add(eventFactory.createAttribute(VERSION, "1.4"));
        xmlWriter.add(eventFactory.createStartElement(TMX, attrs.iterator(), null));
    }
    
    public void endTMX() throws XMLStreamException {
        xmlWriter.add(eventFactory.createEndElement(TMX, null));
        xmlWriter.add(eventFactory.createEndDocument());
    }
    
    public void startHeader(Header h) throws XMLStreamException {
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        // TODO: enforce attr requirements
        addAttr(attrs, CREATIONID, h.getCreationId());
        addAttr(attrs, CREATIONDATE, h.getCreationDate());
        addAttr(attrs, CREATIONTOOL, h.getCreationTool());
        addAttr(attrs, CREATIONTOOLVERSION, h.getCreationToolVersion());
        addAttr(attrs, SEGTYPE, h.getSegType());
        addAttr(attrs, TMF, h.getTmf());
        addAttr(attrs, ADMINLANG, h.getAdminLang());
        addAttr(attrs, SRCLANG, h.getSrcLang());
        addAttr(attrs, SEGTYPE, h.getSegType());
        addAttr(attrs, CHANGEDATE, h.getChangeDate());
        addAttr(attrs, CHANGEID, h.getChangeId());
        addAttr(attrs, ENCODING, h.getEncoding());
        addAttr(attrs, DATATYPE, h.getDataType());
        xmlWriter.add(eventFactory.createStartElement(HEADER, attrs.iterator(), null));
    }
    
    private void addAttr(List<Attribute> attrs, QName qname, String value) {
        if (value != null) {
            attrs.add(eventFactory.createAttribute(qname, value));
        }
    }
    
    public void endHeader() throws XMLStreamException {
        xmlWriter.add(eventFactory.createEndElement(HEADER, null));
    }
    
    public void writeProperty(String type, String value) throws XMLStreamException {
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        attrs.add(eventFactory.createAttribute(TYPE, type));
        xmlWriter.add(eventFactory.createStartElement(PROPERTY, attrs.iterator(), null));
        xmlWriter.add(eventFactory.createCharacters(value));
        xmlWriter.add(eventFactory.createEndElement(PROPERTY, null));
    }
    
    public void writeNote(String value) throws XMLStreamException {
        xmlWriter.add(eventFactory.createStartElement(NOTE, null, null));
        xmlWriter.add(eventFactory.createCharacters(value));
        xmlWriter.add(eventFactory.createEndElement(NOTE, null));
    }
    
    public void startBody() throws XMLStreamException {
        xmlWriter.add(eventFactory.createStartElement(BODY, null, null));
    }
    
    public void endBody() throws XMLStreamException {
        xmlWriter.add(eventFactory.createEndElement(BODY, null));
    }
    
    public void writeTu(TU tu) throws XMLStreamException {
        // TODO: TU attributes
        xmlWriter.add(eventFactory.createStartElement(TU, null, null));
        // TODO: Always print the source first
        Map<String, TUV> tuvs = tu.getTuvs();
        for (Map.Entry<String, TUV> e : tuvs.entrySet()) {
            ArrayList<Attribute> attrs = new ArrayList<Attribute>();
            attrs.add(eventFactory.createAttribute(XMLLANG, e.getKey()));
            xmlWriter.add(eventFactory.createStartElement(TUV, attrs.iterator(), null));
            xmlWriter.add(eventFactory.createStartElement(SEG, null, null));
            for (TUVContent content : e.getValue().getContents()) {
                writeContent(content);
            }
            xmlWriter.add(eventFactory.createEndElement(SEG, null));
            xmlWriter.add(eventFactory.createEndElement(TUV, null));
        }
        xmlWriter.add(eventFactory.createEndElement(TU, null));
    }
    
    private void writeContents(List<TUVContent> contents) throws XMLStreamException { 
        for (TUVContent content : contents) {
            writeContent(content);
        }
    }
    
    private void writeContent(TUVContent content) throws XMLStreamException {
        if (content instanceof SimpleContent) {
            xmlWriter.add(eventFactory.createCharacters(((SimpleContent)content).getValue()));
        }
        else if (content instanceof HiTag) {
            
        }
        else if (content instanceof BptTag) {
            
        }
        else if (content instanceof EptTag) {
            
        }
        else if (content instanceof PhTag) {
            writePh((PhTag)content);
        }
        else if (content instanceof ItTag) {
            
        }
        else if (content instanceof Subflow) {
            
        }
    }
    
    private void writePh(PhTag ph) throws XMLStreamException {
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        if (ph.getX() != ph.NO_VALUE) {
            attrs.add(eventFactory.createAttribute(X, Integer.toString(ph.getX())));
        }
        if (ph.getType() != null) {
            attrs.add(eventFactory.createAttribute(TYPE, ph.getType()));
        }
        if (ph.getAssoc() != null) {
            attrs.add(eventFactory.createAttribute(ASSOC, ph.getAssoc()));
        }
        xmlWriter.add(eventFactory.createStartElement(PH, attrs.iterator(), null));
        writeContents(ph.getContents());
        xmlWriter.add(eventFactory.createEndElement(PH, null));
    }
}

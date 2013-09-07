package com.spartansoftwareinc.otter;

import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

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
        eventFactory = eventFactory.newInstance();
    }
    
    @Deprecated
    private void TODO() {
        throw new UnsupportedOperationException();
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
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        xmlWriter.add(eventFactory.createStartElement(NOTE, attrs.iterator(), null));
        xmlWriter.add(eventFactory.createCharacters(value));
        xmlWriter.add(eventFactory.createEndElement(NOTE, null));
    }
    
    public void startBody() throws XMLStreamException {
        xmlWriter.add(eventFactory.createStartElement(BODY, null, null));
    }
    
    public void endBody() throws XMLStreamException {
        xmlWriter.add(eventFactory.createEndElement(BODY, null));
    }
}

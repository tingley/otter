package com.spartansoftwareinc.otter;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;

import static com.spartansoftwareinc.otter.TMXConstants.*;

public class TMXWriter {

    public static TMXWriter createTMXEventWriter(Writer w) 
                throws XMLStreamException {
        return new TMXWriter(w);
    }

    private XMLEventWriter xmlWriter;
    private XMLEventFactory eventFactory;
    
    private TMXWriter(Writer w) throws XMLStreamException {
        XMLOutputFactory factory = XMLOutputFactory.newFactory();
        xmlWriter = factory.createXMLEventWriter(w);
        eventFactory = XMLEventFactory.newInstance();
    }
        
    public void writeEvent(TMXEvent event) throws XMLStreamException {
        switch (event.getEventType()) {
        case START_TMX:
            startTMX();
            break;
        case END_TMX:
            endTMX();
            break;
        case HEADER:
            writeHeader(event.getHeader());
            break;
        case START_BODY:
            startBody();
            break;
        case END_BODY:
            endBody();
            break;
        case TU:
            writeTu(event.getTU());
            break;
        default:
            throw new UnsupportedOperationException();
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
    
    public void writeHeader(Header h) throws XMLStreamException {
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        // TODO: enforce attr requirements
        addAttr(attrs, CREATIONID, h.getCreationId());
        if (h.getCreationDate() != null) {
            addAttr(attrs, CREATIONDATE, Util.writeTMXDate(h.getCreationDate()));
        }
        addAttr(attrs, CREATIONTOOL, h.getCreationTool());
        addAttr(attrs, CREATIONTOOLVERSION, h.getCreationToolVersion());
        addAttr(attrs, SEGTYPE, h.getSegType());
        addAttr(attrs, TMF, h.getTmf());
        addAttr(attrs, ADMINLANG, h.getAdminLang());
        addAttr(attrs, SRCLANG, h.getSrcLang());
        addAttr(attrs, SEGTYPE, h.getSegType());
        if (h.getChangeDate() != null) {
            addAttr(attrs, CHANGEDATE, Util.writeTMXDate(h.getChangeDate()));
        }
        addAttr(attrs, CHANGEID, h.getChangeId());
        addAttr(attrs, ENCODING, h.getEncoding());
        addAttr(attrs, DATATYPE, h.getDataType());
        xmlWriter.add(eventFactory.createStartElement(HEADER, attrs.iterator(), null));
        for (Property property : h.getProperties()) {
            writeProperty(property.getType(), property.getValue());
        }
        for (Note note : h.getNotes()) {
            writeNote(note.getContent());
        }
        xmlWriter.add(eventFactory.createEndElement(HEADER, null));
    }
    
    private void addAttr(List<Attribute> attrs, QName qname, String value) {
        if (value != null) {
            attrs.add(eventFactory.createAttribute(qname, value));
        }
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
        ArrayList<Attribute> tuAttrs = new ArrayList<Attribute>();
        attr(tuAttrs, TUID, tu.getId());
        attr(tuAttrs, ENCODING, tu.getEncoding());
        attr(tuAttrs, DATATYPE, tu.getDatatype());
        if (tu.getUsageCount() != null) {
            attr(tuAttrs, USAGECOUNT, tu.getUsageCount().toString());
        }
        dateAttr(tuAttrs, LASTUSAGEDATE, tu.getLastUsageDate());
        attr(tuAttrs, CREATIONTOOL, tu.getCreationTool());
        attr(tuAttrs, CREATIONTOOLVERSION, tu.getCreationToolVersion());
        dateAttr(tuAttrs, CREATIONDATE, tu.getCreationDate());
        attr(tuAttrs, CREATIONID, tu.getCreationId());
        dateAttr(tuAttrs, CHANGEDATE, tu.getChangeDate());
        attr(tuAttrs, SEGTYPE, tu.getSegType());
        attr(tuAttrs, CHANGEID, tu.getChangeId());
        attr(tuAttrs, TMF, tu.getTmf());
        attr(tuAttrs, SRCLANG, tu.getSrcLang());
        xmlWriter.add(eventFactory.createStartElement(TU, tuAttrs.iterator(), null));
        // TODO: Always print the source first
        Map<String, TUV> tuvs = tu.getTuvs();
        for (Map.Entry<String, TUV> e : tuvs.entrySet()) {
            ArrayList<Attribute> attrs = new ArrayList<Attribute>();
            attr(attrs, XMLLANG, e.getKey());
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
    
    private void writeTag(QName qname, List<Attribute> attrs, List<TUVContent> contents) 
                          throws XMLStreamException {
        xmlWriter.add(eventFactory.createStartElement(qname, attrs.iterator(), null));
        for (TUVContent content : contents) {
            writeContent(content);
        }
        xmlWriter.add(eventFactory.createEndElement(qname, null));
    }

    private void writeContent(TUVContent content) throws XMLStreamException {
        if (content instanceof SimpleContent) {
            xmlWriter.add(eventFactory.createCharacters(((SimpleContent)content).getValue()));
        }
        else if (content instanceof PlaceholderTag) {
            writePh((PlaceholderTag)content);
        }
        else if (content instanceof BeginTag) {
            writeBpt((BeginTag)content);
        }
        else if (content instanceof EndTag) {
            writeEpt((EndTag)content);
        }
        else if (content instanceof IsolatedTag) {
            writeIt((IsolatedTag)content);
        }
        else if (content instanceof HighlightTag) {
            writeHi((HighlightTag)content);
        }
        else if (content instanceof Subflow) {
            writeSubflow((Subflow)content);
        }
    }
    
    private void writePh(PlaceholderTag ph) throws XMLStreamException {
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        if (ph.getX() != PlaceholderTag.NO_VALUE) {
            attrs.add(eventFactory.createAttribute(X, Integer.toString(ph.getX())));
        }
        attr(attrs, TYPE, ph.getType());
        attr(attrs, ASSOC, ph.getAssoc());
        writeTag(PH, attrs, ph.getContents());
    }
    
    private void writeBpt(BeginTag bpt) throws XMLStreamException {
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        attrs.add(eventFactory.createAttribute(I, Integer.toString(bpt.getI())));
        if (bpt.getX() != BeginTag.NO_VALUE) {
            attrs.add(eventFactory.createAttribute(X, Integer.toString(bpt.getX())));
        }
        attr(attrs, TYPE, bpt.getType());
        writeTag(BPT, attrs, bpt.getContents());
    }
    
    private void writeEpt(EndTag ept) throws XMLStreamException {
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        attrs.add(eventFactory.createAttribute(I, Integer.toString(ept.getI())));
        writeTag(EPT, attrs, ept.getContents());
    }
    
    private void writeIt(IsolatedTag it) throws XMLStreamException {
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        attrs.add(eventFactory.createAttribute(POS, it.getPos().getAttrValue()));
        if (it.getX() != IsolatedTag.NO_VALUE) {
            attrs.add(eventFactory.createAttribute(X, Integer.toString(it.getX())));
        }
        attr(attrs, TYPE, it.getType());
        writeTag(IT, attrs, it.getContents());
    }

    private void writeHi(HighlightTag hi) throws XMLStreamException {
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        if (hi.getX() != IsolatedTag.NO_VALUE) {
            attrs.add(eventFactory.createAttribute(X, Integer.toString(hi.getX())));
        }
        attr(attrs, TYPE, hi.getType());
        writeTag(HI, attrs, hi.getContents());
    }
    
    private void writeSubflow(Subflow sub) throws XMLStreamException {
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        attr(attrs, TYPE, sub.getType());
        attr(attrs, DATATYPE, sub.getDatatype());
        writeTag(SUB, attrs, sub.getContents());
    }
    
    private void attr(List<Attribute> attrs, QName name, String value) {
        if (value != null) {
            attrs.add(eventFactory.createAttribute(name, value));
        }
    }
    private void dateAttr(List<Attribute> attrs, QName name, Date value) {
        if (value != null) {
            attrs.add(eventFactory.createAttribute(name, Util.writeTMXDate(value)));
        }
    }
}
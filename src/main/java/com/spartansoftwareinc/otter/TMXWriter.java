package com.spartansoftwareinc.otter;

import java.io.Writer;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;

import static com.spartansoftwareinc.otter.TMXConstants.*;

/**
 * A class to write TMX 1.4b documents.  A <code>TMXWriter</code>
 * may be either fed raw {@link TMXEvent} objects (such as those
 * provided from a {@link TMXReader}, or individual methods may
 * be called to write out particular document structures.
 * <p>
 * As a TMX document is structured, <code>TMXWriter</code> requires
 * that certain types of data be written at certain places in the document.
 * For example, an attempt to call {@link #writeHeader}
 * after a call to {@link #startBody} has been made will result in an
 * error.
 * <p>
 * <code>TMXWriter</code> interacts with a <code>Writer</code>
 * rather than an <code>OutputStream</code>, so it does not specify
 * the encoding of the data it writes.  Furthermore, it does not write
 * a Unicode byte-order mark (BOM).  If a BOM is needed, it should be 
 * written out explicitly before passing the <code>Writer</code> to
 * <code>TMXWriter</code>. 
 * <p>
 * <code>TMXWriter</code> uses the {@link XMLEventWriter} mechanism
 * to construct the underlying XML.  As a result, it is stream-based and
 * does not hold the entire document in memory.
 * 
 * @see <a href="http://www.gala-global.org/oscarStandards/tmx/">http://www.gala-global.org/oscarStandards/tmx/</a>
 */
public class TMXWriter {

    /**
     * Create a new <code>TMXWriter</code> that writes TMX to the specified 
     * <code>Writer</code>.
     * @param w 
     * @return
     * @throws XMLStreamException
     */
    public static TMXWriter createTMXEventWriter(Writer w) 
                throws XMLStreamException {
        return new TMXWriter(w);
    }

    // Thread-safe after initialization
    private static XMLOutputFactory factory = XMLOutputFactory.newFactory();
    
    private XMLEventWriter xmlWriter;
    // XMLEventFactory is not thread-safe 
    private XMLEventFactory eventFactory;
    private String headerSrcLang;
    
    private TMXWriter(Writer w) throws XMLStreamException {
        xmlWriter = factory.createXMLEventWriter(w);
        eventFactory = XMLEventFactory.newInstance();
    }
    
    /**
     * Write a raw {@link TMXEvent}.
     * @param event
     * @throws XMLStreamException
     */
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
    
    /**
     * Begin writing a TMX document, including the <code>&lt;tmx&gt;</code>
     * element itself and version information.
     * @throws XMLStreamException
     */
    public void startTMX() throws XMLStreamException {
        xmlWriter.add(eventFactory.createStartDocument());
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        attrs.add(eventFactory.createAttribute(VERSION, "1.4"));
        xmlWriter.add(eventFactory.createStartElement(TMX, attrs.iterator(), null));
    }
    
    /**
     * Finish writing a TMX document.
     * @throws XMLStreamException
     */
    public void endTMX() throws XMLStreamException {
        xmlWriter.add(eventFactory.createEndElement(TMX, null));
        xmlWriter.add(eventFactory.createEndDocument());
    }
    
    /**
     * Write a TMX Header.  This should include any notes or properties
     * that should be included in the header declaration.
     * @param h header information, including any notes and properties
     * @throws XMLStreamException if an XML error occurs
     * @throws OtterException if an invalid header is passed
     */
    public void writeHeader(Header h) throws XMLStreamException {
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        addAttr(attrs, CREATIONID, h.getCreationId(), false);
        if (h.getCreationDate() != null) {
            addAttr(attrs, CREATIONDATE, Util.writeTMXDate(h.getCreationDate()), false);
        }
        addAttr(attrs, CREATIONTOOL, h.getCreationTool(), true);
        addAttr(attrs, CREATIONTOOLVERSION, h.getCreationToolVersion(), true);
        addAttr(attrs, SEGTYPE, h.getSegType(), true);
        addAttr(attrs, TMF, h.getTmf(), true);
        addAttr(attrs, ADMINLANG, h.getAdminLang(), true);
        addAttr(attrs, SRCLANG, h.getSrcLang(), true);
        addAttr(attrs, SEGTYPE, h.getSegType(), true);
        if (h.getChangeDate() != null) {
            addAttr(attrs, CHANGEDATE, Util.writeTMXDate(h.getChangeDate()), false);
        }
        addAttr(attrs, CHANGEID, h.getChangeId(), false);
        addAttr(attrs, ENCODING, h.getEncoding(), false);
        addAttr(attrs, DATATYPE, h.getDataType(), true);
        xmlWriter.add(eventFactory.createStartElement(HEADER, attrs.iterator(), null));
        for (Property property : h.getProperties()) {
            writeProperty(property);
        }
        for (Note note : h.getNotes()) {
            writeNote(note);
        }
        xmlWriter.add(eventFactory.createEndElement(HEADER, null));
        headerSrcLang = h.getSrcLang();
    }
    
    private void addAttr(List<Attribute> attrs, QName qname, String value, boolean required) {
        if (value != null) {
            attrs.add(eventFactory.createAttribute(qname, value));
        }
        else if (required) {
            throw new OtterException("Header is missing required attribute '" + qname + "'");
        }
    }
    
    void writeProperty(Property prop) throws XMLStreamException {
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        attrs.add(eventFactory.createAttribute(TYPE, prop.getType()));
        if (prop.getEncoding() != null) {
            attrs.add(eventFactory.createAttribute(ENCODING, prop.getEncoding()));
        }
        if (prop.getLang() != null) {
            attrs.add(eventFactory.createAttribute(XMLLANG, prop.getLang()));
        }
        xmlWriter.add(eventFactory.createStartElement(PROPERTY, attrs.iterator(), null));
        xmlWriter.add(eventFactory.createCharacters(prop.getValue()));
        xmlWriter.add(eventFactory.createEndElement(PROPERTY, null));
    }
    
    void writeNote(Note note) throws XMLStreamException {
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        if (note.getEncoding() != null) {
            attrs.add(eventFactory.createAttribute(ENCODING, note.getEncoding()));
        }
        if (note.getLang() != null) {
            attrs.add(eventFactory.createAttribute(XMLLANG, note.getLang()));
        }
        xmlWriter.add(eventFactory.createStartElement(NOTE, attrs.iterator(), null));
        xmlWriter.add(eventFactory.createCharacters(note.getContent()));
        xmlWriter.add(eventFactory.createEndElement(NOTE, null));
    }
    
    /**
     * Begin writing the body of a TMX document.  All TUs must
     * be written within the body.
     * @throws XMLStreamException
     */
    public void startBody() throws XMLStreamException {
        xmlWriter.add(eventFactory.createStartElement(BODY, null, null));
    }
    
    /**
     * Finish writing the body of a TMX document.
     * @throws XMLStreamException
     */
    public void endBody() throws XMLStreamException {
        xmlWriter.add(eventFactory.createEndElement(BODY, null));
    }
    
    /**
     * Write a {@link TU} and all of its data.
     * @param tu
     * @throws XMLStreamException
     */
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

        // Language codes are case-insensitive, so normalize them
        // to check for the source.
        Map<String, TUV> tuvs = normalizeLocaleMap(tu.getTuvs());
        // Always print the source language first, if present 
        TUV srcTuv = tuvs.remove(headerSrcLang.toLowerCase());
        if (srcTuv != null) {
            writeTuv(srcTuv);
        }
        for (TUV tuv : tuvs.values()) {
            writeTuv(tuv);
        }
        xmlWriter.add(eventFactory.createEndElement(TU, null));
    }

    private Map<String, TUV> normalizeLocaleMap(Map<String, TUV> original) {
        Map<String, TUV> updated = new HashMap<String, TUV>();
        for (Map.Entry<String, TUV> e : original.entrySet()) {
            updated.put(e.getKey().toLowerCase(), e.getValue());
        }
        return updated;
    }

    private void writeTuv(TUV tuv) throws XMLStreamException {
        ArrayList<Attribute> attrs = new ArrayList<Attribute>();
        attr(attrs, XMLLANG, tuv.getLocale());
        xmlWriter.add(eventFactory.createStartElement(TUV, attrs.iterator(), null));
        xmlWriter.add(eventFactory.createStartElement(SEG, null, null));
        convertUnmatchedPairedTags(tuv);
        for (TUVContent content : tuv.getContents()) {
            writeContent(content);
        }
        xmlWriter.add(eventFactory.createEndElement(SEG, null));
        xmlWriter.add(eventFactory.createEndElement(TUV, null));
    }
    
    /**
     * Rewrite any unmatched paired tags as isolated tags.
     * @param tuv TUV to search for unmatched tags
     */
    private void convertUnmatchedPairedTags(TUV tuv) {
        List<TUVContent> contents = tuv.getContents();
        int len = contents.size();
        BitSet bptIValues = new BitSet(len);
        BitSet eptIValues = new BitSet(len);
        for (int i = 0; i < len; i++) {
        	TUVContent c = contents.get(i);
        	if (c instanceof BeginTag) {
        		bptIValues.set(((BeginTag)c).getI());
        	}
        	else if (c instanceof EndTag) {
        		eptIValues.set(((EndTag)c).getI());
        	}
        }
        for (int i = 0; i < len; i++) {
        	TUVContent c = contents.get(i);
        	if (c instanceof BeginTag) {
        		BeginTag bpt = (BeginTag)c;
        		// Check for ept with the same i value
        		if (!eptIValues.get(bpt.getI())) {
        			IsolatedTag it = new IsolatedTag(IsolatedTag.Pos.BEGIN);
        			if (bpt.getX() != BeginTag.NO_VALUE) {
        				it.setX(bpt.getX());
        			}
        			if (bpt.getType() != null) {
        				it.setType(bpt.getType());
        			}
        			contents.set(i, it);
        		}
        	}
        	else if (c instanceof EndTag) {
        		EndTag ept = (EndTag)c;
        		// Check for bpt with the same i value
    			if (!bptIValues.get(ept.getI())) {
        			contents.set(i, new IsolatedTag(IsolatedTag.Pos.END));
        		}
        	}
        }
        
        // TODO: what is the correct behavior if you try to write ept 
        // before its corresponding bpt?
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
        else {
            // Check for tags with missing attributes
            if (content instanceof InlineTag && 
                !((InlineTag)content).hasRequiredAttributes()) {
                    throw new OtterException("Tag is missing required attributes: "
                                             + content);
            }
            if (content instanceof PlaceholderTag) {
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

package com.spartansoftwareinc.otter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.spartansoftwareinc.otter.TMXEventType.*;

import org.junit.*;
import static org.junit.Assert.*;

public class TestTMXEventReader {

    @Test
    public void testHeader() throws Exception {
        InputStream is = getClass().getResourceAsStream("/header.tmx");
        // XXX Who handles BOMs?
        TMXEventReader reader = TMXEventReader.createTMXEventReader(
                            new InputStreamReader(is, "UTF-8"));
        List<TMXEvent> events = readEvents(reader);
        assertNotNull(events);
        checkEvent(events.get(0), START_TMX);
        // Check the header
        checkEvent(events.get(1), START_HEADER);
        Header header = events.get(1).getHeader();
        assertNotNull(header);
        assertEquals("TRADOS Translator's Workbench for Windows", header.getCreationTool());
        assertEquals("Edition 8 Build 863", header.getCreationToolVersion());
        assertEquals("sentence", header.getSegType());
        assertEquals("TW4Win 2.0 Format", header.getTmf());
        assertEquals("EN-US", header.getAdminLang());
        assertEquals("EN-US", header.getSrcLang());
        assertEquals("rtf", header.getDataType());
        // Optional ones
        assertEquals("20100223T044327Z", header.getCreationDate());
        assertEquals("TESTERSON", header.getCreationId());
        assertNull(header.getEncoding());
        assertNull(header.getChangeDate());
        assertNull(header.getChangeId());

        checkProperty(events.get(2), "type1", "Property");
        checkProperty(events.get(3), "type2", "Property with o-encoding");
        checkProperty(events.get(4), "type3", "Property with lang \"fr\"");
        checkNote(events.get(5), "This is a note with an encoding.");
        checkNote(events.get(6), "This is a note with lang \"en\".");
        checkEvent(events.get(7), END_HEADER);
        checkEvent(events.get(8), START_BODY);
        checkEvent(events.get(9), END_BODY);
        checkEvent(events.get(10), END_TMX);
    }
    
    @SuppressWarnings("serial")
    @Test
    public void testBody() throws Exception {
        InputStream is = getClass().getResourceAsStream("/body.tmx");
        TMXEventReader reader = TMXEventReader.createTMXEventReader(
                            new InputStreamReader(is, "UTF-8"));
        List<TU> tus = readTUs(reader);
        assertNotNull(tus);
        assertEquals(2, tus.size());
        TU tu = tus.get(0);
        Map<String, TUV> tuvs = tu.getTuvs();
        assertEquals(2, tuvs.size());
        TUV enTuv = tuvs.get("EN-US");
        assertNotNull(enTuv);
        assertEquals(1, enTuv.getContents().size());
        assertEquals(new TextContent("Hello world!"), enTuv.getContents().get(0));
        TUV frTuv = tuvs.get("FR-FR");
        assertNotNull(frTuv);
        assertEquals(1, frTuv.getContents().size());
        assertEquals(new TextContent("Bonjour tout le monde!"), frTuv.getContents().get(0));
       
        tu = tus.get(1);
        tuvs = tu.getTuvs();
        enTuv = tuvs.get("EN-US");
        assertNotNull(enTuv);
        assertEquals(new ArrayList<TUVContent>(){{
            add(new TextContent("Simple "));
            add(new PhTag(1, "<br/>"));
            add(new TextContent(" tags."));
        }}, enTuv.getContents());
        frTuv = tuvs.get("FR-FR");
        assertNotNull(frTuv);
        assertEquals(new ArrayList<TUVContent>(){{
            add(new TextContent("Simple "));
            add(new PhTag(1, "<br/>"));
            add(new TextContent(" tags (French)."));
        }}, frTuv.getContents());        
    }
    
    @SuppressWarnings("serial")
    @Test
    public void testPairedTags() throws Exception {
        InputStream is = getClass().getResourceAsStream("/paired_tags.tmx");
        TMXEventReader reader = TMXEventReader.createTMXEventReader(
                            new InputStreamReader(is, "UTF-8"));
        List<TU> tus = readTUs(reader);
        assertNotNull(tus);
        assertEquals(1, tus.size());
        TU tu = tus.get(0);
        Map<String, TUV> tuvs = tu.getTuvs();
        TUV enTuv = tuvs.get("EN-US");
        assertNotNull(enTuv);
        assertEquals(new ArrayList<TUVContent>(){{
            add(new TextContent("Simple "));
            add(new BptTag(1, 1, "<b>"));
            add(new TextContent("paired tags"));
            add(new EptTag(1, "</b>"));
            add(new TextContent("."));
        }}, enTuv.getContents());
        TUV frTuv = tuvs.get("FR-FR");
        assertNotNull(frTuv);
        assertEquals(new ArrayList<TUVContent>(){{
            add(new TextContent("Simple "));
            add(new BptTag(1, 1, "<b>"));
            add(new TextContent("paired tags"));
            add(new EptTag(1, "</b>"));
            add(new TextContent(" (French)."));
        }}, frTuv.getContents());        
    }
    
    @SuppressWarnings("serial")
    @Test
    public void testIsolatedTags() throws Exception {
        InputStream is = getClass().getResourceAsStream("/it_tag.tmx");
        TMXEventReader reader = TMXEventReader.createTMXEventReader(
                            new InputStreamReader(is, "UTF-8"));
        List<TU> tus = readTUs(reader);
        assertNotNull(tus);
        assertEquals(2, tus.size());
        TU tu = tus.get(0);
        Map<String, TUV> tuvs = tu.getTuvs();
        TUV enTuv = tuvs.get("EN-US");
        assertNotNull(enTuv);
        assertEquals(new ArrayList<TUVContent>(){{
            add(new TextContent("Simple "));
            add(new ItTag(1, "<b>", ItTag.Pos.BEGIN));
            add(new TextContent("isolated tags."));
        }}, enTuv.getContents());
        TUV frTuv = tuvs.get("FR-FR");
        assertNotNull(frTuv);
        assertEquals(new ArrayList<TUVContent>(){{
            add(new TextContent("Simple "));
            add(new ItTag(1, "<b>", ItTag.Pos.BEGIN));
            add(new TextContent("isolated tags (French)."));
        }}, frTuv.getContents());
        
        tu = tus.get(1);
        tuvs = tu.getTuvs();
        enTuv = tuvs.get("EN-US");
        assertNotNull(enTuv);
        assertEquals(new ArrayList<TUVContent>(){{
            add(new TextContent("Isolated tags are "));
            add(new ItTag(1, "</b>", ItTag.Pos.END));
            add(new TextContent("split across segments."));
        }}, enTuv.getContents());
        frTuv = tuvs.get("FR-FR");
        assertNotNull(frTuv);
        assertEquals(new ArrayList<TUVContent>(){{
            add(new TextContent("Isolated tags are "));
            add(new ItTag(1, "</b>", ItTag.Pos.END));
            add(new TextContent("split across segments (French)."));
        }}, frTuv.getContents());     
    }
    
    @SuppressWarnings("serial")
    @Test
    public void testHighlightTags() throws Exception {
        InputStream is = getClass().getResourceAsStream("/hi_tag.tmx");
        TMXEventReader reader = TMXEventReader.createTMXEventReader(
                            new InputStreamReader(is, "UTF-8"));
        List<TU> tus = readTUs(reader);
        assertNotNull(tus);
        assertEquals(1, tus.size());
        TU tu = tus.get(0);
        Map<String, TUV> tuvs = tu.getTuvs();
        TUV enTuv = tuvs.get("EN-US");
        assertNotNull(enTuv);
        List<TUVContent> enTuvContents = enTuv.getContents();
        assertEquals(3, enTuvContents.size());
        assertEquals(new TextContent("Content containing "), enTuvContents.get(0));
        assertTrue(enTuvContents.get(1) instanceof HiTag);
        HiTag hi = (HiTag)enTuvContents.get(1);
        assertEquals(1, hi.getX());
        assertNull(hi.getType());
        assertEquals(new ArrayList<TUVContent>(){{
            add(new TextContent("highlighted text including "));
            add(new BptTag(2, 1, "<b>"));
            add(new TextContent("tag content"));
            add(new EptTag(1, "</b>"));
        }}, hi.getContents());
        assertEquals(new TextContent("."), enTuvContents.get(2));
        
        TUV frTuv = tuvs.get("FR-FR");
        assertNotNull(frTuv);
        List<TUVContent> frTuvContents = frTuv.getContents();
        assertEquals(3, frTuvContents.size());
        assertEquals(new TextContent("Content containing "), frTuvContents.get(0));
        assertTrue(frTuvContents.get(1) instanceof HiTag);
        hi = (HiTag)frTuvContents.get(1);
        assertEquals(1, hi.getX());
        assertNull(hi.getType());
        assertEquals(new ArrayList<TUVContent>(){{
            add(new TextContent("highlighted text including "));
            add(new BptTag(2, 1, "<b>"));
            add(new TextContent("tag content"));
            add(new EptTag(1, "</b>"));
        }}, hi.getContents());
        assertEquals(new TextContent("."), frTuvContents.get(2));
        
    }
    
    private void checkProperty(TMXEvent e, String propertyType, String value) {
        checkEvent(e, HEADER_PROPERTY);
        Property p = e.getProperty();
        assertNotNull(p);
        assertEquals(propertyType, p.getType());
        assertEquals(value, p.getValue());
    }
    private void checkNote(TMXEvent e, String note) {
        checkEvent(e, HEADER_NOTE);
        Note n = e.getNote();
        assertNotNull(n);
        assertEquals(note, n.getContent());
    }
    private void checkEvent(TMXEvent e, TMXEventType type) {
        assertEquals(type, e.getEventType());
    }
    
    private List<TU> readTUs(TMXEventReader r) throws Exception {
        List<TMXEvent> events = readEvents(r);
        List<TU> tus = new ArrayList<TU>();
        for (TMXEvent e : events) {
            if (e.getEventType() == TU) {
                assertNotNull(e.getTU());
                tus.add(e.getTU());
            }
        }
        return tus;
    }
    
    private List<TMXEvent> readEvents(TMXEventReader r) throws Exception {
        List<TMXEvent> events = new ArrayList<TMXEvent>();
        while (r.hasNext()) {
            events.add(r.nextEvent());
        }
        return events;
    }
}

package com.spartansoftwareinc.otter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.spartansoftwareinc.otter.TMXEventType.*;
import static com.spartansoftwareinc.otter.TestUtil.*;

import org.junit.*;
import static org.junit.Assert.*;

public class TestTMXEventReader {

    @Test
    public void testDateFormat() {
        assertNotNull(Util.parseTMXDate("20100223T044327Z"));
    }
    
    @Test
    public void testBOM() throws Exception {
        TMXReader reader = TestUtil.getTMXReader("/bom.tmx");
        List<TU> tus = readTUs(reader);
        assertEquals(1, tus.size());
    }
    
    @Test
    public void testHeader() throws Exception {
        TMXReader reader = TestUtil.getTMXReader("/header.tmx");
        List<TMXEvent> events = readEvents(reader);
        assertNotNull(events);
        checkEvent(events.get(0), START_TMX);
        // Check the header
        checkEvent(events.get(1), HEADER);
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
        assertEquals(Util.parseTMXDate("20100223T044327Z"), header.getCreationDate());
        assertEquals("TESTERSON", header.getCreationId());
        assertNull(header.getEncoding());
        assertNull(header.getChangeDate());
        assertNull(header.getChangeId());

        List<Property> properties = header.getProperties();
        assertEquals(3, properties.size());
        checkProperty(properties.get(0), "type1", "Property", null, null);
        checkProperty(properties.get(1), "type2", "Property with o-encoding", "ASCII", null);
        checkProperty(properties.get(2), "type3", "Property with lang \"fr\"", null, "fr");
        List<Note> notes = header.getNotes();
        assertEquals(2, notes.size());
        assertEquals(new Note("This is a note with an encoding.").setEncoding("ASCII"), notes.get(0));
        assertEquals(new Note("This is a note with lang \"en\".").setLang("en"), notes.get(1));
        checkEvent(events.get(2), START_BODY);
        checkEvent(events.get(3), END_BODY);
        checkEvent(events.get(4), END_TMX);
    }
    
    @SuppressWarnings("serial")
    @Test
    public void testBody() throws Exception {
        TMXReader reader = TestUtil.getTMXReader("/body.tmx");
        List<TU> tus = readTUs(reader);
        assertNotNull(tus);
        assertEquals(2, tus.size());
        TU tu = tus.get(0);
        assertEquals(Util.parseTMXDate("20130314T060143Z"), tu.getCreationDate());
        assertEquals("TESTA", tu.getCreationId());
        assertEquals(Util.parseTMXDate("20130314T060951Z"), tu.getChangeDate());
        assertEquals("TESTB", tu.getChangeId());
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
            add(new PlaceholderTag(1, "<br/>"));
            add(new TextContent(" tags."));
        }}, enTuv.getContents());
        frTuv = tuvs.get("FR-FR");
        assertNotNull(frTuv);
        assertEquals(new ArrayList<TUVContent>(){{
            add(new TextContent("Simple "));
            add(new PlaceholderTag(1, "<br/>"));
            add(new TextContent(" tags (French)."));
        }}, frTuv.getContents());        
    }
    
    @Test
    public void testTuvPropertiesAndNotes() throws Exception {
        TMXReader reader = TestUtil.getTMXReader("/tuv_properties.tmx");
        List<TU> tus = readTUs(reader);
        assertNotNull(tus);
        assertEquals(1, tus.size());
        Map<String, TUV> tuvs = tus.get(0).getTuvs();
        TUV enTuv = tuvs.get("EN-US");
        assertNotNull(enTuv);
        assertEquals(0, enTuv.getProperties().size());
        List<Note> tuvNotes = enTuv.getNotes();
        assertEquals(1, tuvNotes.size());
        assertEquals("Note content", tuvNotes.get(0).getContent());
        TUV frTuv = tuvs.get("FR-FR");
        assertNotNull(frTuv);
        List<Property> tuvProperties = frTuv.getProperties();
        assertEquals(1, tuvProperties.size());
        assertEquals("x-type", tuvProperties.get(0).getType());
        assertEquals("TEXT", tuvProperties.get(0).getValue());
        assertEquals(0, frTuv.getNotes().size());
    }
    
    @Test
    public void testTuPropertiesAndNotes() throws Exception {
        TMXReader reader = TestUtil.getTMXReader("/tu_properties.tmx");
        List<TU> tus = readTUs(reader);
        assertNotNull(tus);
        assertEquals(1, tus.size());
        TU tu = tus.get(0);
        List<Property> properties = tu.getProperties();
        assertEquals(1, properties.size());
        assertEquals("x-type", properties.get(0).getType());
        assertEquals("TEXT", properties.get(0).getValue());

        List<Note> notes = tu.getNotes();
        assertEquals(1, notes.size());
        assertEquals("Note content", notes.get(0).getContent());
    }
    
    @SuppressWarnings("serial")
    @Test
    public void testPairedTags() throws Exception {
        TMXReader reader = TestUtil.getTMXReader("/paired_tags.tmx");
        List<TU> tus = readTUs(reader);
        assertNotNull(tus);
        assertEquals(1, tus.size());
        TU tu = tus.get(0);
        Map<String, TUV> tuvs = tu.getTuvs();
        TUV enTuv = tuvs.get("EN-US");
        assertNotNull(enTuv);
        assertEquals(new ArrayList<TUVContent>(){{
            add(new TextContent("Simple "));
            add(new BeginTag(1, 1, "<b>"));
            add(new TextContent("paired tags"));
            add(new EndTag(1, "</b>"));
            add(new TextContent("."));
        }}, enTuv.getContents());
        TUV frTuv = tuvs.get("FR-FR");
        assertNotNull(frTuv);
        assertEquals(new ArrayList<TUVContent>(){{
            add(new TextContent("Simple "));
            add(new BeginTag(1, 1, "<b>"));
            add(new TextContent("paired tags"));
            add(new EndTag(1, "</b>"));
            add(new TextContent(" (French)."));
        }}, frTuv.getContents());        
    }
    
    @SuppressWarnings("serial")
    @Test
    public void testIsolatedTags() throws Exception {
        TMXReader reader = TestUtil.getTMXReader("/it_tag.tmx");
        List<TU> tus = readTUs(reader);
        assertNotNull(tus);
        assertEquals(2, tus.size());
        TU tu = tus.get(0);
        Map<String, TUV> tuvs = tu.getTuvs();
        TUV enTuv = tuvs.get("EN-US");
        assertNotNull(enTuv);
        assertEquals(new ArrayList<TUVContent>(){{
            add(new TextContent("Simple "));
            add(new IsolatedTag(IsolatedTag.Pos.BEGIN, 1, "<b>"));
            add(new TextContent("isolated tags."));
        }}, enTuv.getContents());
        TUV frTuv = tuvs.get("FR-FR");
        assertNotNull(frTuv);
        assertEquals(new ArrayList<TUVContent>(){{
            add(new TextContent("Simple "));
            add(new IsolatedTag(IsolatedTag.Pos.BEGIN, 1, "<b>"));
            add(new TextContent("isolated tags (French)."));
        }}, frTuv.getContents());
        
        tu = tus.get(1);
        tuvs = tu.getTuvs();
        enTuv = tuvs.get("EN-US");
        assertNotNull(enTuv);
        assertEquals(new ArrayList<TUVContent>(){{
            add(new TextContent("Isolated tags are "));
            add(new IsolatedTag(IsolatedTag.Pos.END, 1, "</b>"));
            add(new TextContent("split across segments."));
        }}, enTuv.getContents());
        frTuv = tuvs.get("FR-FR");
        assertNotNull(frTuv);
        assertEquals(new ArrayList<TUVContent>(){{
            add(new TextContent("Isolated tags are "));
            add(new IsolatedTag(IsolatedTag.Pos.END, 1, "</b>"));
            add(new TextContent("split across segments (French)."));
        }}, frTuv.getContents());     
    }
    
    @SuppressWarnings("serial")
    @Test
    public void testHighlightTags() throws Exception {
        TMXReader reader = TestUtil.getTMXReader("/hi_tag.tmx");
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
        assertTrue(enTuvContents.get(1) instanceof HighlightTag);
        HighlightTag hi = (HighlightTag)enTuvContents.get(1);
        assertEquals(1, hi.getX());
        assertNull(hi.getType());
        assertEquals(new ArrayList<TUVContent>(){{
            add(new TextContent("highlighted text including "));
            add(new BeginTag(2, 1, "<b>"));
            add(new TextContent("tag content"));
            add(new EndTag(1, "</b>"));
        }}, hi.getContents());
        assertEquals(new TextContent("."), enTuvContents.get(2));
        
        TUV frTuv = tuvs.get("FR-FR");
        assertNotNull(frTuv);
        List<TUVContent> frTuvContents = frTuv.getContents();
        assertEquals(3, frTuvContents.size());
        assertEquals(new TextContent("Content containing "), frTuvContents.get(0));
        assertTrue(frTuvContents.get(1) instanceof HighlightTag);
        hi = (HighlightTag)frTuvContents.get(1);
        assertEquals(1, hi.getX());
        assertNull(hi.getType());
        assertEquals(new ArrayList<TUVContent>(){{
            add(new TextContent("highlighted text including "));
            add(new BeginTag(2, 1, "<b>"));
            add(new TextContent("tag content"));
            add(new EndTag(1, "</b>"));
        }}, hi.getContents());
        assertEquals(new TextContent("."), frTuvContents.get(2));
    }
    
    @Test
    public void testNestedHighlightTags() throws Exception {
        TMXReader reader = TestUtil.getTMXReader("/hi_nested.tmx");
        List<TU> tus = readTUs(reader);
        assertNotNull(tus);
        assertEquals(1, tus.size());
        TU tu = tus.get(0);
        Map<String, TUV> tuvs = tu.getTuvs();
        checkNestedHighlightTagsTuv(tuvs.get("EN-US"));
        checkNestedHighlightTagsTuv(tuvs.get("FR-FR"));
    }
    
    @SuppressWarnings("serial")
    private void checkNestedHighlightTagsTuv(TUV tuv) {
        assertNotNull(tuv);
        List<TUVContent> tuvContents = tuv.getContents();
        
        assertEquals(3, tuvContents.size());
        assertEquals(new TextContent("A"), tuvContents.get(0));
        assertTrue(tuvContents.get(1) instanceof HighlightTag);
        HighlightTag hi1 = (HighlightTag)tuvContents.get(1);
        assertEquals(1, hi1.getX());
        List<TUVContent> hiContents1 = hi1.getContents();
        assertEquals(3, hiContents1.size());
        assertEquals(new TextContent("B"), hiContents1.get(0));
        assertTrue(hiContents1.get(1) instanceof HighlightTag);
        HighlightTag hi2 = (HighlightTag)hiContents1.get(1);
        assertEquals(2, hi2.getX());
        List<TUVContent> hiContents2 = hi2.getContents();
        assertEquals(new ArrayList<TUVContent>(){{
            add(new TextContent("C"));
        }}, hiContents2);
        assertEquals(new TextContent("D"), hiContents1.get(2));
        assertEquals(new TextContent("."), tuvContents.get(2));
    }
    
    @Test
    public void testSubflows() throws Exception {
        TMXReader reader = TestUtil.getTMXReader("/subflow.tmx");
        List<TU> tus = readTUs(reader);
        assertNotNull(tus);
        assertEquals(4, tus.size());
        TU tu = tus.get(0);
        Map<String, TUV> tuvs = tu.getTuvs();
        checkBptSubflowTuv(tuvs.get("EN-US"));
        checkBptSubflowTuv(tuvs.get("FR-FR"));
        tu = tus.get(1);
        tuvs = tu.getTuvs();
        checkEptSubflowTuv(tuvs.get("EN-US"));
        checkEptSubflowTuv(tuvs.get("FR-FR"));
        tu = tus.get(2);
        tuvs = tu.getTuvs();
        checkPhSubflowTuv(tuvs.get("EN-US"));
        checkPhSubflowTuv(tuvs.get("FR-FR"));
        tu = tus.get(3);
        tuvs = tu.getTuvs();
        checkItSubflowTuv(tuvs.get("EN-US"));
        checkItSubflowTuv(tuvs.get("FR-FR"));
    }
    
    @Test
    public void testTUEventSequenceNumbers() throws Exception {
        TMXReader reader = TestUtil.getTMXReader("/subflow.tmx");
        List<TMXEvent> events = readEvents(reader);
        List<TUEvent> tuEvents = new ArrayList<TUEvent>();
        for (TMXEvent e : events) {
            if (e.getEventType() == TMXEventType.TU){
                assertTrue(e instanceof TUEvent);
                tuEvents.add((TUEvent)e);
            }
        }
        assertEquals(4, tuEvents.size());
        for (int i = 0; i < tuEvents.size(); i++) {
            assertEquals(i, tuEvents.get(i).getSequence());
        }
    }
    
    private void checkBptSubflowTuv(TUV tuv) {
        assertNotNull(tuv);
        List<TUVContent> tuvContents = tuv.getContents();
        
        assertEquals(5, tuvContents.size());
        assertEquals(new TextContent("Tag containing "), tuvContents.get(0));
        assertTrue(tuvContents.get(1) instanceof BeginTag);
        BeginTag bpt = (BeginTag)tuvContents.get(1);
        assertEquals(1, bpt.getX());
        assertEquals(1, bpt.getI());
        List<TUVContent> bptContents = bpt.getContents();
        assertEquals(3, bptContents.size());
        assertEquals(new CodeContent("<a href=\"#\" title=\""), bptContents.get(0));
        checkSubflow(bptContents.get(1));
        assertEquals(new CodeContent("\">"), bptContents.get(2));
        assertEquals(new TextContent("a subflow"), tuvContents.get(2));
        assertTrue(tuvContents.get(3) instanceof EndTag);
        assertEquals(1, ((EndTag)tuvContents.get(3)).getI());
        List<TUVContent> eptContents = ((EndTag)tuvContents.get(3)).getContents();
        assertEquals(1, eptContents.size());
        assertEquals(new CodeContent("</a>"), eptContents.get(0));
        assertEquals(new TextContent("."), tuvContents.get(4));
    }

    private void checkEptSubflowTuv(TUV tuv) {
        assertNotNull(tuv);
        List<TUVContent> tuvContents = tuv.getContents();
        
        assertEquals(5, tuvContents.size());
        assertEquals(new TextContent("Tag containing "), tuvContents.get(0));
        assertTrue(tuvContents.get(1) instanceof BeginTag);
        BeginTag bpt = (BeginTag)tuvContents.get(1);
        assertEquals(1, bpt.getX());
        assertEquals(1, bpt.getI());
        List<TUVContent> bptContents = bpt.getContents();
        assertEquals(1, bptContents.size());
        assertEquals(new CodeContent("<a href=\"#\">"), bptContents.get(0));
        
        assertEquals(new TextContent("a subflow"), tuvContents.get(2));
        assertTrue(tuvContents.get(3) instanceof EndTag);
        EndTag ept = (EndTag)tuvContents.get(3);
        assertEquals(1, ept.getI());
        List<TUVContent> eptContents = ept.getContents();
        assertEquals(3, eptContents.size());
        assertEquals(new CodeContent("</a "), eptContents.get(0));
        checkSubflow(eptContents.get(1));
        assertEquals(new CodeContent(">"), eptContents.get(2));
        assertEquals(new TextContent(" in the ept."), tuvContents.get(4));
    }
    
    private void checkPhSubflowTuv(TUV tuv) {
        assertNotNull(tuv);
        List<TUVContent> tuvContents = tuv.getContents();
        
        assertEquals(3, tuvContents.size());
        assertEquals(new TextContent("Tag containing "), tuvContents.get(0));
        assertTrue(tuvContents.get(1) instanceof PlaceholderTag);
        PlaceholderTag ph = (PlaceholderTag)tuvContents.get(1);
        assertEquals(1, ph.getX());
        assertEquals("footnote", ph.getType());
        List<TUVContent> phContents = ph.getContents();
        assertEquals(3, phContents.size());
        assertEquals(new CodeContent("<footnote text=\""), phContents.get(0));
        checkSubflow(phContents.get(1));
        assertEquals(new CodeContent("\"/>"), phContents.get(2));
        assertEquals(new TextContent(" a subflow in a ph."), tuvContents.get(2));
    }

    private void checkItSubflowTuv(TUV tuv) {
        assertNotNull(tuv);
        List<TUVContent> tuvContents = tuv.getContents();
        
        assertEquals(3, tuvContents.size());
        assertEquals(new TextContent("Tag containing "), tuvContents.get(0));
        assertTrue(tuvContents.get(1) instanceof IsolatedTag);
        IsolatedTag it = (IsolatedTag)tuvContents.get(1);
        assertEquals(1, it.getX());
        assertEquals(IsolatedTag.Pos.BEGIN, it.getPos());
        List<TUVContent> itContents = it.getContents();
        assertEquals(3, itContents.size());
        assertEquals(new CodeContent("<a href=\"#\" title=\""), itContents.get(0));
        checkSubflow(itContents.get(1));
        assertEquals(new CodeContent("\">"), itContents.get(2));
        assertEquals(new TextContent(" a subflow in an unterminated anchor."), tuvContents.get(2));
    }
    
    private void checkSubflow(TUVContent subflowContent) {
        assertTrue(subflowContent instanceof Subflow);
        Subflow subflow = (Subflow)subflowContent;
        List<TUVContent> subflowContents = subflow.getContents();
        assertEquals(1, subflowContents.size());
        assertEquals(new TextContent("Subflow text"), subflowContents.get(0));
    }
    
}

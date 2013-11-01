package com.spartansoftwareinc.otter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import static com.spartansoftwareinc.otter.TMXEventType.*;
import static com.spartansoftwareinc.otter.TestUtil.*;

import org.junit.*;
import static org.junit.Assert.*;

public class TestTMXEventWriter {

    @Test
    public void testSimple() throws Exception {
        File tmp = File.createTempFile("otter", ".tmx");
        Writer w = new OutputStreamWriter(new FileOutputStream(tmp), "UTF-8");
        TMXWriter writer = TMXWriter.createTMXEventWriter(w);
        writer.startTMX();
        Header header = getHeader();
        writer.writeHeader(header);
        writer.startBody();
        writer.endBody();
        writer.endTMX();
        w.close();
        Reader r = new InputStreamReader(new FileInputStream(tmp), "UTF-8");
        TMXReader reader = TMXReader.createTMXEventReader(r);
        List<TMXEvent> events = readEvents(reader);
        checkEvent(events.get(0), TMXEventType.START_TMX);
        checkEvent(events.get(1), TMXEventType.HEADER);
        Header rHeader = events.get(1).getHeader();
        assertNotNull(rHeader);
        assertEquals(header, rHeader);
        checkProperty(header.getProperties().get(0), "type1", "Property");
        checkNote(header.getNotes().get(0), "This is a note");
        checkEvent(events.get(2), START_BODY);
        checkEvent(events.get(3), END_BODY);
        checkEvent(events.get(4), END_TMX);
        tmp.delete();
    }
    
    @Test
    public void testUnmatchedPairedTagConversion() throws Exception {
        File tmp = File.createTempFile("otter", ".tmx");
        Writer w = new OutputStreamWriter(new FileOutputStream(tmp), "UTF-8");
        TMXWriter writer = TMXWriter.createTMXEventWriter(w);
        writer.startTMX();
        writer.writeHeader(getHeader());
        writer.startBody();
        TU tu = new TU();
        TUV src = new TUV("en-US");
        src.addContent(new TextContent("Dangling "));
        src.addContent(new BeginTag(1));
        src.addContent(new TextContent(" tag"));
        tu.addTUV(src);
        writer.writeEvent(new TUEvent(tu));
        writer.endBody();
        writer.endTMX();
        w.close();
        Reader r = new InputStreamReader(new FileInputStream(tmp), "UTF-8");
        TMXReader reader = TMXReader.createTMXEventReader(r);
        List<TU> tus = readTUs(reader);
        assertEquals(1, tus.size());
        TU tgtTu = tus.get(0);
        Map<String, TUV> tuvs = tgtTu.getTuvs();
        assertEquals(1, tuvs.size());
        TUV tgtTuv = tuvs.get("en-US");
        assertNotNull(tgtTuv);
        List<TUVContent> contents = tgtTuv.getContents();
        assertEquals(3, contents.size());
        assertEquals(new TextContent("Dangling "), contents.get(0));
        IsolatedTag it = new IsolatedTag(IsolatedTag.Pos.BEGIN);
        assertEquals(it, contents.get(1));
        assertEquals(new TextContent(" tag"), contents.get(2));
    }
    
    private Header getHeader() {
        Header header = new Header();
        header.setCreationTool("Otter TMX");
        header.setCreationToolVersion("1.0");
        header.setSegType("sentence");
        header.setTmf("Otter TMX");
        header.setAdminLang("en-US");
        header.setSrcLang("en-US");
        header.setDataType("text");
        header.addProperty(new Property("type1", "Property"));
        header.addNote(new Note("This is a note"));
        return header;
    }
    
    @Test
    public void testRoundtripHeader() throws Exception {
        testRoundtrip("/header.tmx");
    }
    
    @Test
    public void testRoundtripBody() throws Exception {
        testRoundtrip("/body.tmx");
    }
    
    @Test
    public void testRoundtripPairedTags() throws Exception {
        testRoundtrip("/paired_tags.tmx");
    }

    @Test
    public void testRoundtripIsolatedTags() throws Exception {
        testRoundtrip("/it_tag.tmx");
    }
    
    @Test
    public void testHiTags() throws Exception {
        testRoundtrip("/hi_tag.tmx");
    }

    @Test
    public void testNestedHiTags() throws Exception {
        testRoundtrip("/hi_nested.tmx");
    }
    
    @Test
    public void testSubflow() throws Exception {
        testRoundtrip("/subflow.tmx");
    }
    
    public void testRoundtrip(String resourceName) throws Exception {
        InputStream is = getClass().getResourceAsStream(resourceName);
        TMXReader reader = TMXReader.createTMXEventReader(
                            new InputStreamReader(is, "UTF-8"));
        File tmp = File.createTempFile("otter", ".tmx");
        List<TMXEvent> events = readEvents(reader);
        Writer w = new OutputStreamWriter(new FileOutputStream(tmp), "UTF-8");
        TMXWriter writer = TMXWriter.createTMXEventWriter(w);
        for (TMXEvent e : events) {
            writer.writeEvent(e);
        }
        w.close();
        
        // Now verify!
        TMXReader roundtripReader = TMXReader.createTMXEventReader(
                        new InputStreamReader(new FileInputStream(tmp), "UTF-8"));
        List<TMXEvent> roundtripEvents = readEvents(roundtripReader);
        assertEquals(events, roundtripEvents);
        tmp.delete();
    }
    
    // TODO: unittest that verifies that we write the required attributes
    // TODO: properties with encoding and xml:lang properties
    // TODO: notes with encoding and xml:lang properties
}

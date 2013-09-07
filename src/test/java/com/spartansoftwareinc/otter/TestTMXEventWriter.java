package com.spartansoftwareinc.otter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import static com.spartansoftwareinc.otter.TMXEventType.*;
import static com.spartansoftwareinc.otter.TestUtil.*;

import org.junit.*;
import static org.junit.Assert.*;

public class TestTMXEventWriter {

    @Test
    public void testSimple() throws Exception {
        File tmp = File.createTempFile("otter", ".tmx");
        Writer w = new OutputStreamWriter(new FileOutputStream(tmp), "UTF-8");
        TMXEventWriter writer = TMXEventWriter.createTMXEventWriter(w);
        writer.startTMX();
        Header header = new Header();
        header.setCreationTool("Otter TMX");
        header.setCreationToolVersion("1.0");
        header.setSegType("sentence");
        header.setTmf("Otter TMX");
        header.setAdminLang("en-US");
        header.setSrcLang("en-US");
        header.setDataType("text");
        writer.startHeader(header);
        writer.writeProperty("type1", "Property");
        writer.writeNote("This is a note");
        writer.endHeader();
        writer.startBody();
        writer.endBody();
        writer.endTMX();
        w.close();
        System.out.println("" + tmp);
        Reader r = new InputStreamReader(new FileInputStream(tmp), "UTF-8");
        TMXEventReader reader = TMXEventReader.createTMXEventReader(r);
        List<TMXEvent> events = readEvents(reader);
        checkEvent(events.get(0), TMXEventType.START_TMX);
        checkEvent(events.get(1), TMXEventType.START_HEADER);
        Header rHeader = events.get(1).getHeader();
        assertNotNull(rHeader);
        assertEquals(header, rHeader);
        checkProperty(events.get(2), "type1", "Property");
        checkNote(events.get(3), "This is a note");
        checkEvent(events.get(4), END_HEADER);
        checkEvent(events.get(5), START_BODY);
        checkEvent(events.get(6), END_BODY);
        checkEvent(events.get(7), END_TMX);
    }
    
    // TODO: unittest that verifies that we write the required attributes
    // TODO: properties with encoding and xml:lang properties
    // TODO: notes with encoding and xml:lang properties
}

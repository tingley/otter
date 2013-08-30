package com.spartansoftwareinc.otter;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.spartansoftwareinc.otter.TMXEventType.*;

import org.junit.*;
import static org.junit.Assert.*;

public class TestOtter {

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
    
    private List<TMXEvent> readEvents(TMXEventReader r) throws Exception {
        List<TMXEvent> events = new ArrayList<TMXEvent>();
        while (r.hasNext()) {
            events.add(r.nextEvent());
        }
        return events;
    }
}

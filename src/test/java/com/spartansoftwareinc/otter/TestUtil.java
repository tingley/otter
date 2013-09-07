package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.TMXEventType.HEADER_NOTE;
import static com.spartansoftwareinc.otter.TMXEventType.HEADER_PROPERTY;
import static com.spartansoftwareinc.otter.TMXEventType.TU;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {
    public static void checkProperty(TMXEvent e, String propertyType, String value) {
        checkEvent(e, HEADER_PROPERTY);
        Property p = e.getProperty();
        assertNotNull(p);
        assertEquals(propertyType, p.getType());
        assertEquals(value, p.getValue());
    }
    public static void checkNote(TMXEvent e, String note) {
        checkEvent(e, HEADER_NOTE);
        Note n = e.getNote();
        assertNotNull(n);
        assertEquals(note, n.getContent());
    }
    public static void checkEvent(TMXEvent e, TMXEventType type) {
        assertEquals(type, e.getEventType());
    }
    
    public static List<TU> readTUs(TMXEventReader r) throws Exception {
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
    
    public static List<TMXEvent> readEvents(TMXEventReader r) throws Exception {
        List<TMXEvent> events = new ArrayList<TMXEvent>();
        while (r.hasNext()) {
            events.add(r.nextEvent());
        }
        return events;
    }

}

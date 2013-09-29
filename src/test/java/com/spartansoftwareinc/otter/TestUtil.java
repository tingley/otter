package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.TMXEventType.TU;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class TestUtil {
    public static void checkProperty(Property p, String propertyType, String value) {
        assertNotNull(p);
        assertEquals(propertyType, p.getType());
        assertEquals(value, p.getValue());
    }
    public static void checkNote(Note n, String note) {
        assertNotNull(n);
        assertEquals(note, n.getContent());
    }
    public static void checkEvent(TMXEvent e, TMXEventType type) {
        assertEquals(type, e.getEventType());
    }
    
    public static List<TU> readTUs(TMXReader r) {
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
    
    public static List<TMXEvent> readEvents(TMXReader r) {
        List<TMXEvent> events = new ArrayList<TMXEvent>();
        while (r.hasNext()) {
            events.add(r.nextEvent());
        }
        return events;
    }
    
    public static TMXReader getTMXReader(String testResource) throws UnsupportedEncodingException {
        InputStream is = TestUtil.class.getResourceAsStream(testResource);
        return TMXReader.createTMXEventReader(
                            new InputStreamReader(is, "UTF-8"));
    }

}

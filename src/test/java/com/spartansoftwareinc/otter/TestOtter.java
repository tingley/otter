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
        // assert size
        checkEvent(events.get(0), START_TMX);
        checkEvent(events.get(1), START_HEADER);
        checkEvent(events.get(2), PROPERTY);
        checkEvent(events.get(3), PROPERTY);
        checkEvent(events.get(4), PROPERTY);
        checkEvent(events.get(5), NOTE);
        checkEvent(events.get(6), NOTE);
        checkEvent(events.get(7), END_HEADER);
        checkEvent(events.get(8), START_BODY);
        checkEvent(events.get(9), END_BODY);
        checkEvent(events.get(10), END_TMX);
    }
    
    private void checkEvent(TMXEvent e, TMXEventType type) {
        assertEquals(type, e.getType());
    }
    
    private List<TMXEvent> readEvents(TMXEventReader r) throws Exception {
        List<TMXEvent> events = new ArrayList<TMXEvent>();
        while (r.hasNext()) {
            events.add(r.nextEvent());
        }
        return events;
    }
}

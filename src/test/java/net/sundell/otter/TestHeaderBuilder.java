package net.sundell.otter;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Objects;

public class TestHeaderBuilder {

    @Test
    public void testFromPreviousHeader() {
        Header h1 = new HeaderBuilder()
            .setCreationTool("Otter TMX")
            .setCreationToolVersion("1.0")
            .setSegType("sentence")
            .setTmf("Otter TMX")
            .setAdminLang("en-US")
            .setSrcLang("en-US")
            .setDataType("text")
            .build();
        Header h2 = new HeaderBuilder(h1)
                .setCreationToolVersion("2.0")
                .setSegType("paragraph")
                .setDataType("xml")
                .build();
        assertEquals(h1.getCreationTool(), h2.getCreationTool());
        assertEquals(h1.getTmf(), h2.getTmf());
        assertEquals(h1.getAdminLang(), h2.getAdminLang());
        assertEquals(h1.getSrcLang(), h2.getSrcLang());
        assertFalse(Objects.equals(h1.getCreationToolVersion(), h2.getCreationToolVersion()));
        assertFalse(Objects.equals(h1.getSegType(), h2.getSegType()));
        assertFalse(Objects.equals(h1.getDataType(), h2.getDataType()));
    }
}

package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.TestUtil.readEvents;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import net.sundell.snax.SNAXUserException;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTMXEventReaderErrors {

    @Test
    public void testXMLError() throws Exception {
        TMXReader reader = TMXReader.createTMXEventReader(
                            new StringReader("asldjalksdjalsdj"));
        TestErrorHandler handler = new TestErrorHandler();
        reader.setErrorHandler(handler);
        readWithErrors(reader);
        assertNotNull(handler.xmlError);
    }
    
    // XXX It would be nice to have checkable error typing
    // and an easier way to verify locations
    
    @Test
    public void testDateFormatError() throws Exception {
        expectNonfatalError("/error_date_format.tmx", 1);
    }

    @Test
    public void testUnsupportedVersionError() throws Exception {
        expectNonfatalError("/error_tmx_version.tmx", 1);
    }

    @Test
    public void testMissingVersionError() throws Exception {
        expectFatalError("/error_missing_tmx_version.tmx");
    }
    
    @Test
    public void testMissingBptIAttrError() throws Exception {
        expectFatalError("/error_missing_bpt_i.tmx");
    }
    
    @Test
    public void testMissingItAttrError() throws Exception {
        expectFatalError("/error_it_tag_missing_pos.tmx");
    }
    
    // Trick here is that I might be adding to a <seg> or a <sub>
    // or a <hi> I need to keep things simple
    @Test
    public void testOutOfOrderPairedTagsError() throws Exception {
        expectFatalError("/error_out_of_order_pair.tmx");
    }
    
    void expectNonfatalError(String resource, int errorCount) throws Exception {
        InputStream is = getClass().getResourceAsStream(resource);
        TMXReader reader = TMXReader.createTMXEventReader(
                            new InputStreamReader(is, "UTF-8"));
        TestErrorHandler handler = new TestErrorHandler();
        reader.setErrorHandler(handler);
        readWithErrors(reader);
        assertEquals(errorCount, handler.errors.size());
        assertNull(handler.fatalError);
        assertNull(handler.xmlError);
    }
    
    void expectFatalError(String resource) throws Exception {
        InputStream is = getClass().getResourceAsStream(resource);
        TMXReader reader = TMXReader.createTMXEventReader(
                            new InputStreamReader(is, "UTF-8"));
        TestErrorHandler handler = new TestErrorHandler();
        reader.setErrorHandler(handler);
        readWithErrors(reader);
        assertEquals(0, handler.errors.size());
        assertNotNull(handler.fatalError);
        assertNull(handler.xmlError);
    }
    
    void readWithErrors(TMXReader reader) {
        try {
            readEvents(reader);
        }
        catch (OtterException e) {
            // Make sure all thrown exceptions originated from 
            // our error framework
            assertEquals("Halting on error", e.getMessage());
        }
        catch (SNAXUserException e) {
            // XXX I don't like how my exceptions are getting masked.
            assertTrue(e.getCause() instanceof OtterException);
            assertEquals("Halting on error", e.getCause().getMessage());
        }
    }
    
    class TestErrorHandler implements ErrorHandler {
        List<OtterException> errors = new ArrayList<OtterException>();
        OtterException fatalError = null;
        XMLStreamException xmlError = null;
        
        @Override
        public void error(OtterException e) {
            errors.add(e);
        }

        @Override
        public void fatalError(OtterException e) {
            fatalError = e;
            throw new OtterException("Halting on error", e.getLocation());
        }

        @Override
        public void xmlError(XMLStreamException e) {
            xmlError = e;
            throw new OtterException("Halting on error", e.getLocation());
        }
    }
}
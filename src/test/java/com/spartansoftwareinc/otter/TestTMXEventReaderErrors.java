package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.TestUtil.readEvents;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;

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

    void readWithErrors(TMXReader reader) {
        try {
            readEvents(reader);
        }
        catch (OtterException e) {
            // Make sure all thrown exceptions originated from 
            // our error framework
            assertEquals("Halting on error", e.getMessage());
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

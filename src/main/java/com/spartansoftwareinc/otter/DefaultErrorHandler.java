package com.spartansoftwareinc.otter;

import javax.xml.stream.XMLStreamException;

/**
 * Default ErrorHandler implementation: throws 
 * exceptions on fatal errors, ignores everything else.
 */
public class DefaultErrorHandler implements ErrorHandler {
    @Override
    public void tuError(int tuSequence, OtterException e) {
        // Do nothing
    }

    @Override
    public void error(OtterException e) {
        // Do nothing   
    }

    @Override
    public void fatalError(OtterException e) {
        throw e;
    }

    @Override
    public void xmlError(XMLStreamException e) {
        throw new OtterException(e, e.getLocation());
    }
}

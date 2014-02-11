package com.spartansoftwareinc.otter;

import javax.xml.stream.XMLStreamException;

/**
 * Default ErrorHandler implementation: throws 
 * exceptions on fatal errors, ignores everything else.
 */
public class DefaultErrorHandler implements ErrorHandler {
    @Override
    public void tuError(int tuSequence, OtterInputException e) {
        // Do nothing
    }

    @Override
    public void error(OtterInputException e) {
        // Do nothing   
    }

    @Override
    public void fatalError(OtterInputException e) {
        throw e;
    }

    @Override
    public void xmlError(XMLStreamException e) {
        throw new OtterInputException(e, e.getLocation());
    }
}

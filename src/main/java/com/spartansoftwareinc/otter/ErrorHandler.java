package com.spartansoftwareinc.otter;

import javax.xml.stream.XMLStreamException;

/**
 * A SAX-style error handling interface.  If no ErrorHandler
 * is registered with a {@link TMXReader}, no errors
 * will be reported except for fatal errors, which will
 * throw {@link OtterException}.
 */
public interface ErrorHandler {

    /**
     * Receive notification of a recoverable error.
     */
    void error(OtterException e);

    /**
     * Receive notification of a fatal error.
     */
    void fatalError(OtterException e);
    
    /**
     * Receive notification of an underlying XML error.
     */
    void xmlError(XMLStreamException e);
}

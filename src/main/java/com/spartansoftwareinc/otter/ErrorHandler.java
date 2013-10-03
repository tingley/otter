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
     * Receive notification of an error in the structure of an individual
     * TU.  The reader will attempt to continue parsing the document, 
     * but will not produce the incorrect TU as an event.
     * @param tuSequence The value of the sequence ID that would have
     *          been attached to this TU, had it been successfully produced
     *          as an event.  See {@link TUEvent#getSequence()}.
     * @param e exception that triggered this error call
     */
    void tuError(int tuSequence, OtterException e);
    
    /**
     * Receive notification of a recoverable error that is not related to a 
     * specific TU.
     * @param e exception that triggered this error call
     */
    void error(OtterException e);

    /**
     * Receive notification of a fatal error.
     * @param e exception that triggered this error call
     */
    void fatalError(OtterException e);
    
    /**
     * Receive notification of an underlying XML error.
     * @param e exception that triggered this error call
     */
    void xmlError(XMLStreamException e);
}

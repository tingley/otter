package com.spartansoftwareinc.otter;

public class OtterException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    OtterException(String message) {
        super(message);
    }
    
    OtterException(Throwable cause) {
        super(cause);
    }

    OtterException(Throwable cause, String message) {
        super(message, cause);
    }
}

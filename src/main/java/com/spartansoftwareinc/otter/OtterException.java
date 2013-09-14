package com.spartansoftwareinc.otter;

import javax.xml.stream.Location;

public class OtterException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private Location location;
    
    OtterException(String message, Location location) {
        super(message);
        this.location = location;
    }
    
    OtterException(Throwable cause, Location location) {
        super(cause);
        this.location = location;
    }
    
}

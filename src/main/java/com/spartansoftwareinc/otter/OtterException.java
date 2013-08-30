package com.spartansoftwareinc.otter;

import javax.xml.stream.Location;

public class OtterException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private String message;
    private Location location;
    
    OtterException(String message, Location location) {
        this.message = message;
        this.location = location;
    }
}

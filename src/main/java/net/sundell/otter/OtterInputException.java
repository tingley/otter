package net.sundell.otter;

import javax.xml.stream.Location;

/**
 * Exception thrown on input processing, where a {@link Location} is
 * available.
 */
public class OtterInputException extends OtterException {
    private static final long serialVersionUID = 1L;

    /**
     * Get the XML Parsing location where this error occurred.
     * @return Location where the error occurred in the XML
     */
    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    private Location location;
    
    OtterInputException(String message, Location location) {
        super(message);
        this.location = location;
    }
    
    OtterInputException(Throwable cause, Location location) {
        super(cause);
        this.location = location;
    }
}

package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.Util.eq;

/**
 * Represents a simplified TMX parsing event.
 * {@link TMXEventType#START_TMX} events include a {@link Header},
 * and {@link TMXEventType#TU} events include a {@link TU}.
 */
public class TMXEvent {
    private TMXEventType eventType;
    private Object resource;
    
    protected TMXEvent(TMXEventType type) {
        this.eventType = type;
    }
    
    protected TMXEvent(TMXEventType type, Object resource) {
        this(type);
        this.resource = resource;
    }

    /**
     * Get the type of this event.
     * @return event type
     */
    public TMXEventType getEventType() {
        return eventType;
    }

    /**
     * For <code>START_TMX</code> events, returns the {@link Header}
     * resource attached to this event.  Otherwise, throws an exception.
     * @return Header associated with this <code>START_TMX</code> event
     * @throws IllegalStateException if called on an invalid event type 
     */
    public Header getHeader() {
        if (eventType == TMXEventType.START_TMX) {
            return (Header)resource;
        }
        throw new IllegalStateException(eventType.toString() + " event is not a header");
    }

    /**
     * For <code>TU</code> events, returns the {@link TU}
     * resource attached to this event.  Otherwise, throws an exception.
     * @return TU associated with this <code>TU</code> event
     * @throws IllegalStateException if called on an invalid event type 
     */
    public TU getTU() {
        if (eventType == TMXEventType.TU) {
            return (TU)resource;
        }
        throw new IllegalStateException(eventType.toString() + " event is not a TU");
    }
    
    @Override
    public String toString() {
        return eventType.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof TMXEvent)) return false;
        TMXEvent e = (TMXEvent)o;
        return eventType.equals(e.eventType) && eq(resource, e.resource);
    }

    @Override
    public int hashCode() {
        return new Hasher().add(eventType).add(resource).value();
    }
}

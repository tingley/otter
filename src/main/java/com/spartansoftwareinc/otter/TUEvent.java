package com.spartansoftwareinc.otter;

/**
 * TMXEvent subclass that represents events of  
 * type {@link TMXEventType#TU}.  This event is produced
 * automatically by the {@link TMXReader} and includes a sequence
 * number that uniquely identifies each TU in the source document.
 * The sequence number is also used when reporting TU errors via  
 * the {@link ErrorHandler} interface.
 */
public class TUEvent extends TMXEvent {
    private int sequence;
    
    protected TUEvent() {
        super(TMXEventType.TU);
    }
    
    protected TUEvent(TU tu) {
        super(TMXEventType.TU, tu);
    }
    
    /**
     * Sequence number that uniquely identifies a TU within this TMX
     * content.  Sequence numbers are 0-indexed. 
     * @return sequence number for this TUEvent
     */
    public int getSequence() {
        return sequence;
    }
    
    void setSequence(int sequence) {
        this.sequence = sequence;
    }
}

package com.spartansoftwareinc.otter;

import java.util.List;

/**
 * Any object that supports TUV content items.
 * <br>
 * Implementing classes may define what subset
 * of TUVContent items they support. 
 */
interface TUVContentSink {
    /**
     * Add an item to the contents of this object.  Depending on the 
     * implementation, not all {@link TUVContent} objects may be allowed.
     * @param content content item to add to this object
     */
    TUVContentSink addContent(TUVContent content);
    
    /**
     * Add multiple items to the contents of this object.  Depending on the 
     * implementation, not all {@link TUVContent} objects may be allowed.
     * 
     * @param contents content items to add to this object
     */
    TUVContentSink addContents(List<TUVContent> contents);
}

package com.spartansoftwareinc.otter;

import java.util.List;

/**
 * Any object that supports TUV content items.
 * <br>
 * Implementing classes may define what subset
 * of TUVContent items they support. 
 */
interface TUVContentSink {
    void addContent(TUVContent content);
    void addContents(List<TUVContent> contents);
}

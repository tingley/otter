package com.spartansoftwareinc.otter;

import java.util.List;

/**
 * Any object that supports TUV content items.
 * <br>
 * Implementing classes may define what subset
 * of TUVContent items they support. 
 */
interface TUVContentSink {
    TUVContentSink addContent(TUVContent content);
    TUVContentSink addContents(List<TUVContent> contents);
}

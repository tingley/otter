package com.spartansoftwareinc.otter;

/**
 * A representation of translatable text content in a TUV.
 */
public class TextContent extends SimpleContent {
    public TextContent(String value) {
        super(value);
    }
    
    @Override
    public boolean equals(Object o) {
        return super.equals(o) &&
                (o instanceof TextContent);
    }
}

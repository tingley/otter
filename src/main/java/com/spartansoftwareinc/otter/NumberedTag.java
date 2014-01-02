package com.spartansoftwareinc.otter;

/**
 * Any inline tag that supports the <code>x</code> attribute.
 */
public interface NumberedTag extends TagContent {

    public int getX();
    
    public NumberedTag setX(int x);
}

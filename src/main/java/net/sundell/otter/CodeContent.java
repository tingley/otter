package net.sundell.otter;

/**
 * A representation of of native code data in a TUV, such as that 
 * found inside a &lt;ph&gt; tag. 
 */
public class CodeContent extends SimpleContent {
    public CodeContent(String value) {
        super(value);
    }
    
    @Override
    public boolean equals(Object o) {
        return super.equals(o) &&
                (o instanceof CodeContent);
    }
}

package com.spartansoftwareinc.otter;

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

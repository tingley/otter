package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.Util.eq;

public class Note {
    private String content;
    
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Note(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof Note)) return false;
        Note n = (Note)o;
        return eq(content, n.content);
    }
}

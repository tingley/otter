package com.spartansoftwareinc.otter;

import java.util.ArrayList;
import java.util.List;
import static com.spartansoftwareinc.otter.Util.eq;

public class Subflow implements TUVContent, TUVContentSink {
    private String type, datatype;
    private List<TUVContent> contents = new ArrayList<TUVContent>();
    
    public Subflow() {
    }
    
    @Override
    public void addContent(TUVContent content) {
        contents.add(content);
    }

    public List<TUVContent> getContents() {
        return contents;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof Subflow)) return false;
        Subflow sub = (Subflow)o;
        return eq(type, sub.type) &&
               eq(datatype, sub.datatype) &&
               contents.equals(sub.contents);
    }
}

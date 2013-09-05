package com.spartansoftwareinc.otter;

import java.util.ArrayList;
import java.util.List;

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
    public String toXML() {
        StringBuilder sb = new StringBuilder();
        for (TUVContent c : contents) {
            sb.append(c.toXML());
        }
        return sb.toString();
    }
}

package com.spartansoftwareinc.otter;

import java.util.List;
import java.util.ArrayList;

public class TUV {
    private String locale;
    private List<TUVContent> contents = new ArrayList<TUVContent>();
 
    public TUV(String locale) {
        this.locale = locale;
    }
    
    public String getLocale() {
        return locale;
    }
    
    public void setLocale(String locale) {
        this.locale = locale;
    }

    public List<TUVContent> getContents() {
        return contents;
    }

    public void setContents(List<TUVContent> contents) {
        this.contents = contents;
    }
    
    public void addContent(TUVContent content) {
        contents.add(content);
    }

}

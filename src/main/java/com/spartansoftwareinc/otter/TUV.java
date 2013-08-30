package com.spartansoftwareinc.otter;

public class TUV {
    private String locale;
    private String content;
 
    public TUV(String locale) {
        this.locale = locale;
    }
    
    public String getLocale() {
        return locale;
    }
    
    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

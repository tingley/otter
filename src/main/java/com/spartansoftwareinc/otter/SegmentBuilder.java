package com.spartansoftwareinc.otter;

import java.util.List;

import javax.xml.stream.events.StartElement;
import static com.spartansoftwareinc.otter.Util.*;
import static com.spartansoftwareinc.otter.TMXConstants.*;

class SegmentBuilder {
    private TU tu;
    private TUV tuv;
    private TMXReader reader;
    
    SegmentBuilder(TMXReader reader) {
        this.reader = reader;
    }
    
    TU getTu() {
        return tu;
    }

    void startTu(StartElement el) {
        ErrorHandler errorHandler = reader.getErrorHandler();
        tu = new TU();
        // Check for optional attributes
        tu.setId(attrVal(el, TUID));
        tu.setEncoding(attrVal(el, ENCODING));
        tu.setDatatype(attrVal(el, DATATYPE));
        tu.setUsageCount(attrValAsInteger(el, USAGECOUNT));
        tu.setLastUsageDate(attrValAsDate(el, LASTUSAGEDATE, errorHandler));
        tu.setCreationTool(attrVal(el, CREATIONTOOL));
        tu.setCreationToolVersion(attrVal(el, CREATIONTOOLVERSION));
        tu.setCreationDate(attrValAsDate(el, CREATIONDATE, errorHandler));
        tu.setCreationId(attrVal(el, CREATIONID));
        tu.setChangeDate(attrValAsDate(el, CHANGEDATE, errorHandler));
        tu.setSegType(attrVal(el, SEGTYPE));
        tu.setChangeId(attrVal(el, CHANGEID));
        tu.setTmf(attrVal(el, TMF));
        tu.setSrcLang(attrVal(el, SRCLANG));
        // TODO: warn if SRCLANG != global SRCLANG
    }
    
    void startTuv(StartElement el) {
        String locale = attrVal(el, XMLLANG);
        require(locale != null, el.getLocation(), "TUV has no xml:lang");
        tuv = new TUV(locale);
        // TODO: test for duplicates
        tu.addTUV(tuv);
    }
    
    void endTuv() {
    }

    TUV getCurrentTuv() {
        return tuv;
    }
    
    List<TUVContent> getTuvContents() {
        return tuv.getContents();
    }
    
}

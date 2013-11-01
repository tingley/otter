package com.spartansoftwareinc.otter;

import java.util.BitSet;
import java.util.List;

import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;

import static com.spartansoftwareinc.otter.Util.*;
import static com.spartansoftwareinc.otter.TMXConstants.*;

class SegmentBuilder {
    private TU tu;
    private TUV tuv;
    private TMXReader reader;
    private BitSet pairedTagIndices = new BitSet(8);
    
    SegmentBuilder(TMXReader reader) {
        this.reader = reader;
    }
    
    TU getTu() {
        return tu;
    }

    void startTu(StartElement el) {
        try {
            tu = new TU();
            // Check for optional attributes
            tu.setId(attrVal(el, TUID));
            tu.setEncoding(attrVal(el, ENCODING));
            tu.setDatatype(attrVal(el, DATATYPE));
            tu.setUsageCount(attrValAsInteger(el, USAGECOUNT));
            tu.setLastUsageDate(attrValAsDate(el, LASTUSAGEDATE, null));
            tu.setCreationTool(attrVal(el, CREATIONTOOL));
            tu.setCreationToolVersion(attrVal(el, CREATIONTOOLVERSION));
            tu.setCreationDate(attrValAsDate(el, CREATIONDATE, null));
            tu.setCreationId(attrVal(el, CREATIONID));
            tu.setChangeDate(attrValAsDate(el, CHANGEDATE, null));
            tu.setSegType(attrVal(el, SEGTYPE));
            tu.setChangeId(attrVal(el, CHANGEID));
            tu.setTmf(attrVal(el, TMF));
            tu.setSrcLang(attrVal(el, SRCLANG));
        }
        catch (OtterException e) {
            reader.reportTuError(e);
        }
        // TODO: warn if SRCLANG != global SRCLANG
    }
    
    void endTu(EndElement el) {
    	// Inherit default srclang if none is set
    	if (tu.getSrcLang() == null) {
    		tu.setSrcLang(reader.getSrcLang());
    	}
    	// Check to make sure that this TU contains a TUV with
    	// the expected source locale
    	if (!tu.getTuvs().keySet().contains(tu.getSrcLang())) {
    		reader.reportTuError(new OtterException(
    				"TU has no TUV with expected source locale " + reader.getSrcLang(),
    				el.getLocation()));
    	}
    }
    
    void startTuv(StartElement el) {
        pairedTagIndices.clear();
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
    
    void startPair(BeginTag bpt, StartElement el) {
        int iValue = bpt.getI();
        if (pairedTagIndices.get(iValue)) {
            reader.reportTuError(new OtterException(
                    "TUV contains multiple bpt tags with index " + iValue, el.getLocation()));
        }
        else {
            pairedTagIndices.set(iValue);
        }
    }
    
    void endPair(EndTag ept, StartElement el) {
        int iValue = ept.getI();
        if (!pairedTagIndices.get(iValue)) {
            reader.reportTuError(new OtterException(
                    "TUV contains ept without a preceding bpt, index " + iValue, el.getLocation()));
        }
        else {
            pairedTagIndices.clear(iValue);
        }
    }
}

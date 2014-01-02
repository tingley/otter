package com.spartansoftwareinc.otter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.spartansoftwareinc.otter.Util.eq;

public class TU {
    private Map<String, TUV> tuvs = new HashMap<String, TUV>();
    private String id, encoding, datatype, creationTool, creationToolVersion, 
        creationId, segType, changeId, tmf, srcLang;
    private Integer usageCount; // optional, so can be null
    private Date lastUsageDate, creationDate, changeDate;
    private List<Property> properties = new ArrayList<Property>();
    private List<Note> notes = new ArrayList<Note>();
    
    public TU() {
    }

    public TU(String srcLang) {
        this.srcLang = srcLang;
    }

    /**
     * Return a {@link TUVBuilder} to build a TUV of the specified 
     * locale.  The {@link TUV} will be added to this TU when 
     * {@link TUVBuilder#build()} is called. 
     * @param locale locale for the TUV to build
     * @return {@link TUVBuilder} instance
     */
    public TUVBuilder tuvBuilder(String locale) {
        return new TUVBuilder(this, locale);
    }
    
    public void addTUV(TUV tuv) {
        tuvs.put(tuv.getLocale(), tuv);
    }
    
    public Map<String, TUV> getTuvs() {
        return tuvs;
    }
    
    public void setTuvs(Map<String, TUV> tuvs) {
        this.tuvs = tuvs;
    }
    
    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public void addProperty(Property property) {
        properties.add(property);
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
    
    public void addNote(Note note) {
        notes.add(note);
    }
    
    @Override
    public int hashCode() {
        return new Hasher()
            .add(id)
            .add(encoding)
            .add(datatype)
            .add(creationTool)
            .add(creationToolVersion)
            .add(creationId)
            .add(segType)
            .add(changeId)
            .add(tmf)
            .add(srcLang)
            .add(usageCount)
            .add(lastUsageDate)
            .add(creationDate)
            .add(changeDate)
            .add(tuvs)
            .add(notes)
            .add(properties)
            .value();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof TU)) return false;
        TU tu = (TU)o;
        return eq(id, tu.id) &&
               eq(encoding, tu.encoding) &&
               eq(datatype, tu.datatype) &&
               eq(creationTool, tu.creationTool) &&
               eq(creationToolVersion, tu.creationToolVersion) &&
               eq(creationId, tu.creationId) && 
               eq(segType, tu.segType) &&
               eq(changeId, tu.changeId) &&
               eq(tmf, tu.tmf) &&
               eq(srcLang, tu.srcLang) &&
               eq(usageCount, tu.usageCount) &&
               eq(lastUsageDate, tu.lastUsageDate) &&
               eq(creationDate, tu.creationDate) &&
               eq(changeDate, tu.changeDate) &&
               tuvs.equals(tu.tuvs) &&
               notes.equals(tu.notes) &&
               properties.equals(tu.properties);
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getEncoding() {
        return encoding;
    }
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }
    public String getDatatype() {
        return datatype;
    }
    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }
    public String getCreationTool() {
        return creationTool;
    }
    public void setCreationTool(String creationTool) {
        this.creationTool = creationTool;
    }
    public String getCreationToolVersion() {
        return creationToolVersion;
    }
    public void setCreationToolVersion(String creationToolVersion) {
        this.creationToolVersion = creationToolVersion;
    }
    public String getCreationId() {
        return creationId;
    }
    public void setCreationId(String creationId) {
        this.creationId = creationId;
    }
    public String getSegType() {
        return segType;
    }
    public void setSegType(String segType) {
        this.segType = segType;
    }
    public String getChangeId() {
        return changeId;
    }
    public void setChangeId(String changeId) {
        this.changeId = changeId;
    }
    public String getTmf() {
        return tmf;
    }
    public void setTmf(String tmf) {
        this.tmf = tmf;
    }
    /**
     * Return the source language for this TU.  When reading from TMX,
     * this value will default to the <code>srclang</code> defined in 
     * the TMX header, unless a local override was provided.
     * @return locale code
     */
    public String getSrcLang() {
        return srcLang;
    }
    public void setSrcLang(String srcLang) {
        this.srcLang = srcLang;
    }
    public Integer getUsageCount() {
        return usageCount;
    }
    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }
    public Date getLastUsageDate() {
        return lastUsageDate;
    }
    public void setLastUsageDate(Date lastUsageDate) {
        this.lastUsageDate = lastUsageDate;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    public Date getChangeDate() {
        return changeDate;
    }
    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }
    
    @Override
    public String toString() {
    	// XXX not including most attrs
    	return "TU(" + tuvs + ")";
    }
}

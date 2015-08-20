package com.spartansoftwareinc.otter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Representation of a <code>tu</code> element.  A TU is the basic
 * unittest of multilingual information in a TMX.  Each TU contains one or more
 * {@link TUV}.  (TUs containing only a single TUV are legal under the specification,
 * although they serve no practical purpose in most situations. Each TU
 * has an optional source language.  When reading TUs from a {@link TMXReader},
 * the source language will be inherited from the TMX header if it is not
 * set explicitly.
 */
public class TU {
    private Map<String, TUV> tuvs = new HashMap<String, TUV>();
    private String id, encoding, datatype, creationTool, creationToolVersion, 
        creationId, segType, changeId, tmf, srcLang;
    private Integer usageCount; // optional, so can be null
    private Date lastUsageDate, creationDate, changeDate;
    private List<Property> properties = new ArrayList<Property>();
    private List<Note> notes = new ArrayList<Note>();

    /**
     * Construct a new TU.
     */
    public TU() {
    }

    /**
     * Construct a new TU with the specified source language.
     */
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

    /**
     * Add a TUV to this TU.
     * @param tuv
     * @return TU with TUV added.
     */
    public TU addTUV(TUV tuv) {
        tuvs.put(tuv.getLocale(), tuv);
        return this;
    }

    // case-insensitive TUV languages?
    // technically, nothing prevents multiple TUVs from
    // having the same language. 
    
    /**
     * Return the TUVs for this TU as a Map, keyed
     * by their locale
     * @return map of language and TUV data 
     */
    public Map<String, TUV> getTuvs() {
        return tuvs;
    }
    
    /**
     * Set TUVs for this TU
     * @param tuvs
     * @return TU instance with updated TUVs
     */
    public TU setTuvs(Map<String, TUV> tuvs) {
        this.tuvs = tuvs;
        return this;
    }

    /**
     * Get properties for this TU.
     * @return properties
     */
    public List<Property> getProperties() {
        return properties;
    }

    /**
     * Set properties for this TU.
     * @param properties
     * @return TU instance with updated properties
     */
    public TU setProperties(List<Property> properties) {
        this.properties = new ArrayList<Property>(properties);
        return this;
    }

    /**
     * Add a property to this TU.
     * @param property
     * @return TU instance with property added
     */
    public TU addProperty(Property property) {
        properties.add(property);
        return this;
    }

    /**
     * Get notes for this TU.
     * @return notes
     */
    public List<Note> getNotes() {
        return notes;
    }

    /**
     * Set notes for this TU.
     * @param notes
     * @return TU instance with updated notes
     */
    public TU setNotes(List<Note> notes) {
        this.notes = notes;
        return this;
    }

    /**
     * Add a note to this TU.
     * @param note
     * @return TU instance with note added
     */
    public TU addNote(Note note) {
        notes.add(note);
        return this;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, encoding, datatype, creationTool, creationToolVersion,
                            segType, changeId, tmf, srcLang, usageCount, lastUsageDate,
                            creationDate, changeDate, tuvs, notes, properties);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof TU)) return false;
        TU tu = (TU)o;
        return Objects.equals(id, tu.id) &&
               Objects.equals(encoding, tu.encoding) &&
               Objects.equals(datatype, tu.datatype) &&
               Objects.equals(creationTool, tu.creationTool) &&
               Objects.equals(creationToolVersion, tu.creationToolVersion) &&
               Objects.equals(creationId, tu.creationId) &&
               Objects.equals(segType, tu.segType) &&
               Objects.equals(changeId, tu.changeId) &&
               Objects.equals(tmf, tu.tmf) &&
               Objects.equals(srcLang, tu.srcLang) &&
               Objects.equals(usageCount, tu.usageCount) &&
               Objects.equals(lastUsageDate, tu.lastUsageDate) &&
               Objects.equals(creationDate, tu.creationDate) &&
               Objects.equals(changeDate, tu.changeDate) &&
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

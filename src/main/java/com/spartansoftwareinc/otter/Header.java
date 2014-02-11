package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.Util.eq;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Header {

    // Required
    private String creationTool;
    private String creationVersion;
    private String segType;
    private String tmf;
    private String adminLang;
    private String srcLang;
    private String dataType;
    // Optional
    private String encoding;
    private Date creationDate;
    private String creationId;
    private Date changeDate;
    private String changeId;
    
    private List<Property> properties = new ArrayList<Property>();
    private List<Note> notes = new ArrayList<Note>();

    public Header() {
    }
    
    public List<Property> getProperties() {
        return properties;
    }
    
    public Header addProperty(Property prop) {
        properties.add(prop);
        return this;
    }
    
    public List<Note> getNotes() {
        return notes;
    }
    
    public Header addNote(Note note) {
        notes.add(note);
        return this;
    }
    
    public String getCreationTool() {
        return creationTool;
    }

    public Header setCreationTool(String creationTool) {
        this.creationTool = creationTool;
        return this;
    }

    public String getCreationToolVersion() {
        return creationVersion;
    }

    public Header setCreationToolVersion(String creationVersion) {
        this.creationVersion = creationVersion;
        return this;
    }

    public String getSegType() {
        return segType;
    }

    public Header setSegType(String segType) {
        this.segType = segType;
        return this;
    }

    public String getTmf() {
        return tmf;
    }

    public Header setTmf(String tmf) {
        this.tmf = tmf;
        return this;
    }

    public String getAdminLang() {
        return adminLang;
    }

    public Header setAdminLang(String adminLang) {
        this.adminLang = adminLang;
        return this;
    }

    public String getSrcLang() {
        return srcLang;
    }

    public Header setSrcLang(String srcLang) {
        this.srcLang = srcLang;
        return this;
    }

    public String getDataType() {
        return dataType;
    }

    public Header setDataType(String dataType) {
        this.dataType = dataType;
        return this;
    }

    public String getEncoding() {
        return encoding;
    }

    public Header setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Header setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public String getCreationId() {
        return creationId;
    }

    public Header setCreationId(String creationId) {
        this.creationId = creationId;
        return this;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public Header setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
        return this;
    }

    public String getChangeId() {
        return changeId;
    }

    public Header setChangeId(String changeId) {
        this.changeId = changeId;
        return this;
    }

    @Override
    public int hashCode() {
        return new Hasher()
            .add(creationTool)
            .add(creationVersion)
            .add(segType)
            .add(tmf)
            .add(adminLang)
            .add(srcLang)
            .add(dataType)
            .add(encoding)
            .add(creationDate)
            .add(creationId)
            .add(changeDate)
            .add(changeId)
            .value();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof Header)) return false;
        Header h = (Header)o;
        return eq(creationTool, h.creationTool) &&
               eq(creationVersion, h.creationVersion) &&
               eq(segType, h.segType) &&
               eq(tmf, h.tmf) &&
               eq(adminLang, h.adminLang) &&
               eq(srcLang, h.srcLang) &&
               eq(dataType, h.dataType) &&
               eq(encoding, h.encoding) &&
               eq(creationDate, h.creationDate) &&
               eq(creationId, h.creationId) &&
               eq(changeDate, h.changeDate) &&
               eq(changeId, h.changeId);
    }
    
}

package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.Util.eq;

import java.util.Date;

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
    
    public Header() {
    }
    
    public String getCreationTool() {
        return creationTool;
    }

    public void setCreationTool(String creationTool) {
        this.creationTool = creationTool;
    }

    public String getCreationToolVersion() {
        return creationVersion;
    }

    public void setCreationToolVersion(String creationVersion) {
        this.creationVersion = creationVersion;
    }

    public String getSegType() {
        return segType;
    }

    public void setSegType(String segType) {
        this.segType = segType;
    }

    public String getTmf() {
        return tmf;
    }

    public void setTmf(String tmf) {
        this.tmf = tmf;
    }

    public String getAdminLang() {
        return adminLang;
    }

    public void setAdminLang(String adminLang) {
        this.adminLang = adminLang;
    }

    public String getSrcLang() {
        return srcLang;
    }

    public void setSrcLang(String srcLang) {
        this.srcLang = srcLang;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreationId() {
        return creationId;
    }

    public void setCreationId(String creationId) {
        this.creationId = creationId;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getChangeId() {
        return changeId;
    }

    public void setChangeId(String changeId) {
        this.changeId = changeId;
    }

    // TODO: hashcode
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

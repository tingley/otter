package com.spartansoftwareinc.otter;

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
    private String creationDate;
    private String creationId;
    private String changeDate;
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

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getCreationId() {
        return creationId;
    }

    public void setCreationId(String creationId) {
        this.creationId = creationId;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
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
        return creationTool.equals(h.creationTool) &&
               creationVersion.equals(h.creationVersion) &&
               segType.equals(h.segType) &&
               tmf.equals(h.tmf) &&
               adminLang.equals(h.adminLang) &&
               srcLang.equals(h.srcLang) &&
               dataType.equals(h.dataType) &&
               encoding.equals(h.encoding) &&
               creationDate.equals(h.creationDate) &&
               creationId.equals(h.creationId) &&
               changeDate.equals(h.changeDate) &&
               changeId.equals(h.changeId);
    }
    
}

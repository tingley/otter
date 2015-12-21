package net.sundell.otter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    Header(String creationTool, String creationVersion, String segType, String tmf, String adminLang,
           String srcLang, String dataType, String encoding, Date creationDate, String creationId,
           Date changeDate, String changeId, List<Property> properties, List<Note> notes) {
        this.creationTool = creationTool;
        this.creationVersion = creationVersion;
        this.segType = segType;
        this.tmf = tmf;
        this.adminLang = adminLang;
        this.srcLang = srcLang;
        this.dataType = dataType;
        this.encoding = encoding;
        this.creationDate = creationDate;
        this.creationId = creationId;
        this.changeDate = changeDate;
        this.changeId = changeId;
        this.properties = properties;
        this.notes = notes;
    }

    /**
     * Get properties for the Header.
     * @return properties
     */
    public List<Property> getProperties() {
        return properties;
    }

    /**
     * Get notes for the Header.
     * @return notes
     */
    public List<Note> getNotes() {
        return notes;
    }

    public String getCreationTool() {
        return creationTool;
    }

    public String getCreationToolVersion() {
        return creationVersion;
    }

    public String getSegType() {
        return segType;
    }

    public String getTmf() {
        return tmf;
    }

    public String getAdminLang() {
        return adminLang;
    }

    public String getSrcLang() {
        return srcLang;
    }

    public String getDataType() {
        return dataType;
    }

    public String getEncoding() {
        return encoding;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public String getCreationId() {
        return creationId;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public String getChangeId() {
        return changeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            creationTool,
            creationVersion,
            segType,
            tmf,
            adminLang,
            srcLang,
            dataType,
            encoding,
            creationDate,
            creationId,
            changeDate,
            changeId);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof Header)) return false;
        Header h = (Header)o;
        return Objects.equals(creationTool, h.creationTool) &&
               Objects.equals(creationVersion, h.creationVersion) &&
               Objects.equals(segType, h.segType) &&
               Objects.equals(tmf, h.tmf) &&
               Objects.equals(adminLang, h.adminLang) &&
               Objects.equals(srcLang, h.srcLang) &&
               Objects.equals(dataType, h.dataType) &&
               Objects.equals(encoding, h.encoding) &&
               Objects.equals(creationDate, h.creationDate) &&
               Objects.equals(creationId, h.creationId) &&
               Objects.equals(changeDate, h.changeDate) &&
               Objects.equals(changeId, h.changeId);
    }
    
}

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

    public Header() {
    }

    /**
     * Get properties for the Header.
     * @return properties
     */
    public List<Property> getProperties() {
        return properties;
    }

    /**
     * Add a property to this TU.
     * @param property
     * @return TU instance with property added
     */
    public Header addProperty(Property prop) {
        properties.add(prop);
        return this;
    }

    /**
     * Set properties for the Header
     * @param properties
     * @return Header instance with updated properties
     */
    public Header setProperties(List<Property> properties) {
        this.properties = new ArrayList<Property>(properties);
        return this;
    }

    /**
     * Get notes for the Header.
     * @return notes
     */
    public List<Note> getNotes() {
        return notes;
    }

    /**
     * Add a note to the Header.
     * @param note
     * @return Header instance with note added
     */
    public Header addNote(Note note) {
        notes.add(note);
        return this;
    }

    /**
     * Set notes for the Header.
     * @param notes
     * @return Header instance with updated notes
     */
    public Header setNotes(List<Note> notes) {
        this.notes = new ArrayList<Note>(notes);
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

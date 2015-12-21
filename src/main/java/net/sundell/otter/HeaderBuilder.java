package net.sundell.otter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HeaderBuilder {
    public HeaderBuilder setCreationTool(String creationTool) {
        this.creationTool = creationTool;
        return this;
    }

    public HeaderBuilder setCreationToolVersion(String creationToolVersion) {
        this.creationToolVersion = creationToolVersion;
        return this;
    }

    public HeaderBuilder setSegType(String segType) {
        this.segType = segType;
        return this;
    }

    public HeaderBuilder setTmf(String tmf) {
        this.tmf = tmf;
        return this;
    }

    public HeaderBuilder setAdminLang(String adminLang) {
        this.adminLang = adminLang;
        return this;
    }

    public HeaderBuilder setSrcLang(String srcLang) {
        this.srcLang = srcLang;
        return this;
    }

    public HeaderBuilder setDataType(String dataType) {
        this.dataType = dataType;
        return this;
    }

    public HeaderBuilder setEncoding(String encoding) {
        this.encoding = encoding;
        return this;
    }

    public HeaderBuilder setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
        return this;
    }

    public HeaderBuilder setCreationId(String creationId) {
        this.creationId = creationId;
        return this;
    }

    public HeaderBuilder setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
        return this;
    }

    public HeaderBuilder setChangeId(String changeId) {
        this.changeId = changeId;
        return this;
    }

    /**
     * Add a header property.
     * @param property
     * @return HeaderBuilder instance with property added
     */
    public HeaderBuilder addProperty(Property prop) {
        properties.add(prop);
        return this;
    }

    /**
     * Set properties for the Header
     * @param properties
     * @return Header instance with updated properties
     */
    public HeaderBuilder setProperties(List<Property> properties) {
        this.properties = new ArrayList<Property>(properties);
        return this;
    }

    /**
     * Add a note to the Header.
     * @param note
     * @return Header instance with note added
     */
    public HeaderBuilder addNote(Note note) {
        notes.add(note);
        return this;
    }

    /**
     * Set notes for the Header.
     * @param notes
     * @return Header instance with updated notes
     */
    public HeaderBuilder setNotes(List<Note> notes) {
        this.notes = new ArrayList<Note>(notes);
        return this;
    }

    // Required
    private String creationTool = null;
    private String creationToolVersion = null;
    private String segType = "sentence";
    private String tmf = null;
    private String adminLang;
    private String srcLang;
    private String dataType = "unknown";

    // Optional
    private String encoding;
    private Date creationDate;
    private String creationId;
    private Date changeDate;
    private String changeId;

    private List<Property> properties = new ArrayList<Property>();
    private List<Note> notes = new ArrayList<Note>();

    public HeaderBuilder() { }

    public HeaderBuilder(Header existingHeader) {
        this.creationTool = existingHeader.getCreationTool();
        this.creationToolVersion = existingHeader.getCreationToolVersion();
        this.segType = existingHeader.getSegType();
        this.tmf = existingHeader.getTmf();
        this.adminLang = existingHeader.getAdminLang();
        this.srcLang = existingHeader.getSrcLang();
        this.dataType = existingHeader.getDataType();
        this.encoding = existingHeader.getEncoding();
        this.creationDate = existingHeader.getCreationDate();
        this.creationId = existingHeader.getCreationId();
        this.changeDate = existingHeader.getChangeDate();
        this.changeId = existingHeader.getChangeId();
        this.properties = new ArrayList<>(existingHeader.getProperties());
        this.notes = new ArrayList<>(existingHeader.getNotes());
    }

    // We default datatype to "unknown"
    // We default segtype to "sentence"
    // We default o-tmf to ""
    public Header build() {
        verifyRequiredFields();
        return new Header(creationTool, creationToolVersion, segType, tmf, adminLang, srcLang, dataType,
                encoding, creationDate, creationId, changeDate, changeId, properties, notes);
    }

    private void verifyRequiredFields() {
        List<String> missingFields = new ArrayList<>();
        if (creationTool == null) {
            missingFields.add("creationtool");
        }
        if (creationToolVersion == null) {
            missingFields.add("creationtoolversion");
        }
        if (tmf == null) {
            missingFields.add("o-tmf");
        }
        if (adminLang == null) {
            missingFields.add("adminlang");
        }
        if (srcLang == null) {
            missingFields.add("srcLang");
        }
        if (dataType == null) {
            missingFields.add("datatype");
        }
        if (segType == null) {
            missingFields.add("segtype");
        }
        if (missingFields.size() > 0) {
            StringBuilder sb = new StringBuilder("Header field(s) not set: ");
            for (int i = 0; i < missingFields.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(missingFields.get(i));
            }
            throw new OtterException(sb.toString());
        }
    }
}

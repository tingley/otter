package com.spartansoftwareinc.otter;

import static com.spartansoftwareinc.otter.Util.eq;

/**
 * A representation of a TUV subflow.  The contents of the subflow
 * correspond to the portion of the TUV delimited by &lt;sub&gt; tags.
 * Subflows can contain a mix of text content and inline tags.
 */
public class Subflow extends BaseTUVContentSink implements TagContent {
    private String type, datatype;
    
    public Subflow() {
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }
    
    @Override
    public int hashCode() {
        return new Hasher(super.hashCode())
            .add(type)
            .add(datatype)
            .value();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && 
                (o instanceof Subflow) &&
                eq(type, ((Subflow)o).type) &&
                eq(datatype, ((Subflow)o).datatype);
    }
}

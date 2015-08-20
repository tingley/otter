package com.spartansoftwareinc.otter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Manipulate dates in the TMX format (eg, 20100223T044327Z).
 *
 * Note: this class is not thread-safe.
 */
public class TMXDateParser {
    // 20100223T044327Z
    public static final String TMX_DATE_FORMAT = "yyyyMMdd'T'HHmmss'Z'";
    private SimpleDateFormat df = new SimpleDateFormat(TMX_DATE_FORMAT);

    /**
     * Parse a date conforming to the TMX date format.  Return the parsed
     * date, or null if the date isn't parseable. 
     * @param s string to parse
     * @return parsed date, or null
     */
    public Date parseDate(String s) {
        try {
            return df.parse(s);
        }
        catch (ParseException e) {
            return null;
        }
    }

    /**
     * Serialize a date in TMX date format.
     * @param d date to serialize
     * @return serialized date
     */
    public String writeDate(Date d) {
        return df.format(d);
    }
}

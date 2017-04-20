package suncertify.db;


/**
 * Class containing a description for a field in a file record
 * @author Diego Amicabile
 */
public class FieldDescription {
    /**
     * Field name length
     */
    byte fieldNameLength;

    /**
     * Field name
     */
    String fieldName;

    /**
     * Field length
     */
    byte fieldLength;

    /**
     * Tostring method, helpful for debugging
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer("");
        buffer.append("FieldDescription(");
        buffer.append("fieldNameLength=");
        buffer.append(fieldNameLength);
        buffer.append(",fieldName=");
        buffer.append(fieldName);
        buffer.append(",fieldLength=");
        buffer.append(fieldLength);
        buffer.append(")");

        return buffer.toString();
    }
}

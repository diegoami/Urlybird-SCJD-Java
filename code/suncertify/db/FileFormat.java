package suncertify.db;


/**
 * Class containing information about the format in which data is stored in the file
 * @author Diego Amicabile
 */
public class FileFormat {
    /**
     * The position at which the data starts
     */
    long dataOffset;

    /**
     * Record length
     */
    long recordLength;

    /**
     * File Total length
     */
    int totalLength;

    /**
     * Number of records in the file
     */
    int totalRecNum;

    /**
     * magic cookie value
     */
    int magicCookieValue;

    /**
     * How many fields in a record
     */
    short numberOfFields;

    /**
     * All fields in the file's records
     */
    FieldDescription[] schemaDescription;

    /**
       * ToString method useful for debugging
       * @return A string representation of the FileFormat
       */
    public String toString() {
        StringBuffer buffer = new StringBuffer("");
        buffer.append("FileFormat(");
        buffer.append("dataOffset=");
        buffer.append(dataOffset);
        buffer.append(",recordLength=");
        buffer.append(recordLength);
        buffer.append(",totalLength=");
        buffer.append(totalLength);
        buffer.append(",totalRecNum=");
        buffer.append(totalRecNum);
        buffer.append(",magicCookieValue=");
        buffer.append(magicCookieValue);
        buffer.append(",numberOfFields=");
        buffer.append(numberOfFields);
        buffer.append(",schemaDescription=FieldDescription[");

        for (int i = 0; i < schemaDescription.length; i++) {
            buffer.append(schemaDescription[i].toString());

            if (i < (schemaDescription.length - 1)) {
                buffer.append(",");
            }
        }

        buffer.append("]");

        return buffer.toString();
    }
}

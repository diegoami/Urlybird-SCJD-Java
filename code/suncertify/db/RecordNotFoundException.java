package suncertify.db;


/**
 * This exception should be thrown whenever a record is not found while performing a
 * search or update operation in our database
 * @author Diego Amicabile
 *
 */
public class RecordNotFoundException extends RuntimeException {
    /**
     * Constructs a <CODE>RecordNotFoundException</CODE> object
     */
    public RecordNotFoundException() {
    }

    /**
     * constructs a <CODE>RecordNotFoundException</CODE> object having an exception message
     * @param msg the exception message
     */
    public RecordNotFoundException(String msg) {
        super(msg);
    }

    /**
     * constructs a <CODE>RecordNotFoundException</CODE> object on top of an exception
     * @param throwable the underlying exception
     */
    public RecordNotFoundException(java.lang.Throwable throwable) {
        super(throwable);
    }
}

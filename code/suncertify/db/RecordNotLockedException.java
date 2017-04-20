package suncertify.db;


/**
 * This exception should be thrown whenever
 * the server a trying to modify a record which is not locked
 * @author Diego Amicabile
 */
public class RecordNotLockedException extends RuntimeException {
    /**
     * Constructs a <CODE>RecordNotLockedException</CODE> object
     */
    public RecordNotLockedException() {
    }

    /**
     * Constructs a <CODE>RecordLockedException</CODE> object having an exception message
     * @param msg the exception message
     */
    public RecordNotLockedException(String msg) {
        super(msg);
    }

    /**
     * Constructs a <CODE>RecordLockedException</CODE> object on top of an exception
     * @param throwable the underlying exception
     */
    public RecordNotLockedException(java.lang.Throwable throwable) {
        super(throwable);
    }
}

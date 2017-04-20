package suncertify.db;


/**
 * This exception should be thrown whenever something unexpected happens
 * when trying to lock / unlock a record
 * @author Diego Amicabile
 */
public class RecordLockedException extends java.lang.RuntimeException {
    /**
     * Constructs a <CODE>RecordLockedException</CODE> object
     */
    public RecordLockedException() {
    }

    /**
     * constructs a <CODE>RecordLockedException</CODE> object having an exception message
     * @param msg the exception message
     */
    public RecordLockedException(String msg) {
        super(msg);
    }

    /**
     * Constructs a <CODE>RecordLockedException</CODE> object on top of an exception
     * @param throwable the underlying exception
     */
    public RecordLockedException(java.lang.Throwable throwable) {
        super(throwable);
    }
}

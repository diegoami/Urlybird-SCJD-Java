package suncertify.db;


/**
 * This exception is thrown whenever there is an attempt to
 * add a record which is already in the database. Never actually thrown
 * from the current application.
 * @author Diego Amicabile
 */
public class DuplicateKeyException extends Exception {
    /**
     * constructs a <CODE>DuplicateKeyException</CODE> object
     */
    public DuplicateKeyException() {
    }

    /**
     * constructs a <CODE>DuplicateKeyException</CODE> object having a message
     * @param msg the exception message
     */
    public DuplicateKeyException(String msg) {
        super(msg);
    }

    /**
     * constructs a <CODE>DuplicateKeyException</CODE> object on top of another exception
     * @param throwable the underlying exception
     */
    public DuplicateKeyException(java.lang.Throwable throwable) {
        super(throwable);
    }
}

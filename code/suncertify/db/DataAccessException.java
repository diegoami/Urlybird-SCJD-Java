package suncertify.db;


/**
 *
 * This is a generic exception thrown when something wrong happens while
 * accessing Records through the <CODE>RecordManager</CODE>
 * @author Diego Amicabile
 *
 **/
public class DataAccessException extends java.lang.RuntimeException {
    /**
     * Constructs a <CODE>DataAccessException</CODE> object
     */
    public DataAccessException() {
    }

    /**
     * constructs a <CODE>DataAccessException</CODE> object having an exception message
     * @param msg the exception message
     */
    public DataAccessException(String msg) {
        super(msg);
    }

    /**
     * constructs a <CODE>DataAccessException</CODE> object on top of an exception
     * @param throwable the underlying exception
     */
    public DataAccessException(java.lang.Throwable throwable) {
        super(throwable);
    }
}

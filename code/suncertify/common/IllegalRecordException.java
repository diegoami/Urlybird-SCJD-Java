package suncertify.common;


/**
 * This exception is thrown whenever there is an attempt to
 * add a record which is already in the database
 * @author Diego Amicabile
 */
public class IllegalRecordException extends java.lang.RuntimeException {
    /**
     * constructs a <CODE>IllegalRecordException</CODE> object
     */
    public IllegalRecordException() {
    }

    /**
     * constructs a <CODE>IllegalRecordException</CODE> object having a message
     * @param msg the exception message
     */
    public IllegalRecordException(String msg) {
        super(msg);
    }

    /**
     * constructs a <CODE>IllegalRecordException</CODE> object on top of another exception
     * @param throwable the underlying exception
     */
    public IllegalRecordException(java.lang.Throwable throwable) {
        super(throwable);
    }
}

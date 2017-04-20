package suncertify.common;


/**
 * Exception thrown when the data inserted by the user cannot be validated
 *
 * @author Diego Amicable
 */
public class ValidateException extends RuntimeException {
    /**
     * Constructs a <CODE>ValidateException</CODE> object
     */
    public ValidateException() {
    }

    /**
     * Constructs a <CODE>ValidateException</CODE> object having an exception message
     * @param msg the exception message
     */
    public ValidateException(String msg) {
        super(msg);
    }

    /**
     * Constructs a <CODE>ValidateException</CODE> object on top of an exception
     * @param throwable the underlying exception
     */
    public ValidateException(java.lang.Throwable throwable) {
        super(throwable);
    }
}

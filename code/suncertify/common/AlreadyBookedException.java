package suncertify.common;


/**
 * This exception is thrown whenever we are trying to book a customer
 * on a record which is already booked
 * @author Diego Amicabile
 *
 */
public class AlreadyBookedException extends java.lang.RuntimeException {
    /**
     * constructs a <CODE>AlreadyBookedException</CODE> object
     */
    public AlreadyBookedException() {
    }

    /**
     * constructs a <CODE>AlreadyBookedException</CODE> object with message string
     * @param msg the message string
     */
    public AlreadyBookedException(String msg) {
        super(msg);
    }

    /**
     * constructs a <CODE>AlreadyBookedException</CODE> object with an exception
     * @param throwable the message string
     */
    public AlreadyBookedException(java.lang.Throwable throwable) {
        super(throwable);
    }
}

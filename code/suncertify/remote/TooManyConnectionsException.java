package suncertify.remote;


/**
 * This exceptions happens when too many clients are connecting to the server
 *
 * @author Diego Amicabile
 */
public class TooManyConnectionsException extends java.rmi.RemoteException {
    /**
     * Creates a <CODE>TooManyConnectionsException</CODE> instance having an error message
     * @param arg the error message
     */
    public TooManyConnectionsException(String arg) {
        super(arg);
    }

    /**
     * Creates a <CODE>TooManyConnectionsException</CODE>  instance having an error message
     * on top of an exception
     * @param arg the error message
     * @param throwable the underlying exception
     */
    public TooManyConnectionsException(String arg, Throwable throwable) {
        super(arg, throwable);
    }
}

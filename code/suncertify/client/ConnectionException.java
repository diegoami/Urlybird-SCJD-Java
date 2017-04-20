package suncertify.client;


/**
 * Exception thrown when connection from the client to the server is not
 * successful
 * @author Diego Amicabile
 *
 */
public class ConnectionException extends java.lang.RuntimeException {
    /**
     * constructs a <CODE>ConnectionException</CODE> object
     */
    ConnectionException() {
    }

    /**
     * Constructs a <CODE>ConnectionException</CODE> object with message string
     * @param msg the message string
     */
    ConnectionException(String msg) {
        super(msg);
    }

    /**
     * Constructs a <CODE>ConnectionException</CODE>  object with an exception
     * @param throwable the message string
     */
    ConnectionException(java.lang.Throwable throwable) {
        super(throwable);
    }
}

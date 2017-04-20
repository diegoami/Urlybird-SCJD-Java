package suncertify.client;


/**
 * Holds all exceptions that may occur in the GuiController
 *
 * @author Diego Amicabile
 */
class GuiControllerException extends Exception {
    /**
     * Creates a default <code>GuiControllerException</code> instance.
     */
    GuiControllerException(String msg) {
        super(msg);
    }

    /**
     * Creates a <code>GuiControllerException</code> instance and chains an
     * exception.
     *
     * @param e The exception to wrap and chain.
     */
    GuiControllerException(Throwable e) {
        super(e);
    }
}

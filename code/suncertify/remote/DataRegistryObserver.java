package suncertify.remote;


/**
 * The interface that is implemented by the object which observes the <CODE>DataRegistry</CODE> and
 * reacts to its messages
 * @author Diego Amicabile
 *
 */
public interface DataRegistryObserver {
    /**
     * Method called when the registry wants to print out a message
     * @param message the message to print out
     */
    public void onRegistryMessage(String message);

    /**
     * Method called when the registry is shutting down
     *
     */
    public void onShutDown();
}

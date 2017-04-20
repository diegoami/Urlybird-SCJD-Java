package suncertify.remote;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;


/**
 *
 * Implementation of the pool of <CODE>RemoteDataFacade</CODE> instances which returns a remote instance of
 * a <CODE>RemoteDataFacade</CODE> when needed, using a round robin algorithm. A maximum of 20 clients is allowed.
 * <P> A method to release and give back a RemoteFacade instance to the pool is available.
 * <P> The name which are given by the pool are in the form DATAFACADEXX where XX is a number from 0 to 20
 * <P> A client should always be associated with just one RemoteFacade instance, and that is enforced by the methods
 * <CODE>registerDataFacade</CODE> and <CODE>releaseDataFacade</CODE>
 *
 * @author Diego Amicabile
 *
 **/
public class RemoteFacadePoolImpl extends UnicastRemoteObject
    implements RemoteDataFacadePool {
    private static final int DATABASE_COUNT = 20;
    static final String RMI_OBJECT_NAME = "DATAFACADE";
    private int robinCounter = 0;
    private Logger log = Logger.getLogger("suncertify.remote");

    /**
     * Collection of strings which are taken now and cannot be associated with a remote object
     */
    private Collection unavailableRemoteObjectNames = new ArrayList();

    /**
     * The <CODE>RemoteFacadePoolImpl</CODE> constructor
     * @throws RemoteException
     */
    RemoteFacadePoolImpl() throws RemoteException {
    }

    /**
     * Register an instance of <CODE>RemoteDataFacade</CODE>  with the RMI server and returns
     * the name which can be used to reference it. The name is saved in the internal list
     * as a string
     * @return The RMI Object name
     * @throws RemoteException
     */
    public synchronized String registerDataFacade() throws RemoteException {
        String result = getAvailableDataFacadeName();
        log.info(Thread.currentThread().getName() +
            " Registering data facade " + result);
        DataRegistry.getInstance().registerRemoteObject(result);

        return result;
    }

    /**
     * Unregister a <CODE>DBMainAdapter</CODE> instance
     * @param remoteObjectName The RMI Object name
     * @throws RemoteException
     */
    public synchronized void releaseDataFacade(String remoteObjectName)
        throws RemoteException {
        log.info(Thread.currentThread().getName() + "Releasing data facade " +
            remoteObjectName);
        removeFromUnavailableRemoteObjects(remoteObjectName);
        DataRegistry.getInstance().unregisterRemoteObject(remoteObjectName);
    }

    /**
     * Gets a name that has not been already assigned to a RemoteDataFacadeImpl. Names are given
     * using a round robin algorithm looping from DATAFACADE1 to DATAFACADE20
     * @return
     * @throws TooManyConnectionsException
     */
    private String getAvailableDataFacadeName()
        throws TooManyConnectionsException {
        int nextry = robinCounter;

        for (int i = 0; i < DATABASE_COUNT; i++) {
            String remoteObjectName = RMI_OBJECT_NAME + (nextry + 1);

            if (!unavailableRemoteObjectNames.contains(remoteObjectName)) {
                unavailableRemoteObjectNames.add(remoteObjectName);
                robinCounter = (nextry + 1) % DATABASE_COUNT;

                return remoteObjectName;
            } else {
                nextry = (nextry + 1) % DATABASE_COUNT;
            }
        }

        throw new TooManyConnectionsException(
            "Too many connections to pool, try later");
    }

    /**
     * Releases a object from the pool of the unavailable (already used) names
     * @param remoteObjectName the name that has to be released
     */
    private void removeFromUnavailableRemoteObjects(String remoteObjectName) {
        if (unavailableRemoteObjectNames.contains(remoteObjectName)) {
            unavailableRemoteObjectNames.remove(remoteObjectName);
        }
    }
}

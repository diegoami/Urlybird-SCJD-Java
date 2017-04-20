package suncertify.remote;

import java.rmi.Naming;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import suncertify.common.Constants;
import suncertify.db.LockManager;


/**
 * <CODE>DataRegistry</CODE> starts the rmi registry. Registers the
 * <CODE>RemoteDataFacadePool </CODE> and provides methods to register and
 * unregister the <CODE>RemoteDataFacade</CODE> instances when they are needed.
 * Keeps a map of the registered remote objects so that they are kept alive and
 * not released immediately.
 * <P><CODE>DataRegistry</CODE> is a singleton.
 *
 * @author Diego Amicabile
 */
public class DataRegistry {
    private static final int DEFAULT_RMI_PORT = 1099;
    private static Logger log = Logger.getLogger("suncertify.remote");
    private static DataRegistry DataRegistry = new DataRegistry();
    private RemoteDataFacadePool pool;
    private Map dataFacadeMap = new HashMap();
    private DataRegistryObserver observer;

    private DataRegistry() {
    }

    /**
     * Returns the only DataRegistry available instance
     * @return the only DataRegistry available instance
     */
    public static DataRegistry getInstance() {
        return DataRegistry;
    }

    /**
     * An observer can be registered. It keeps track of events that happens in the DataRegistry
     * @param newObserver the observer which is about to be registered
     */
    public void setObserver(DataRegistryObserver newObserver) {
        observer = newObserver;
    }

    /**
     * Registers the <CODE>RemoteFacadePool</CODE> instance on the RMI Server
     *
     */
    public void registerPool() {
        try {
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            pool = new RemoteFacadePoolImpl();
            Naming.rebind(Constants.POOL_RMI_NAME, pool);
            observer.onRegistryMessage("Binded " +
                Constants.POOL_RMI_NAME);
            observer.onRegistryMessage("Accepting clients on port " + 1099);
        } catch (Exception e) {
            log.severe(e.toString());
        }
    }

    /**
     * Removes a remote object from the registry, after doing cleanup operations
     * @param remoteObjectName the name which had been used to register the remote object
     */
    public void unregisterRemoteObject(String remoteObjectName) {
        try {
            if (dataFacadeMap.containsKey(remoteObjectName)) {
                RemoteDataFacadeImpl remoteDataFacade = (RemoteDataFacadeImpl) dataFacadeMap.get(remoteObjectName);
                remoteDataFacade.onRemovingFromPool();
            }

            dataFacadeMap.remove(remoteObjectName);
            Naming.unbind(remoteObjectName);
            observer.onRegistryMessage("Unbinded " + remoteObjectName);
        } catch (Exception e) {
            log.warning(e.toString());
        }
    }

    /**
     * Register a <CODE>RemoteDataFacade</CODE> instance in the registry
     * @param remoteObjectName the name used to register the remote object
     */
    public void registerRemoteObject(String remoteObjectName) {
        try {
            RemoteDataFacade remoteObject = new RemoteDataFacadeImpl(remoteObjectName,
                    pool);

            dataFacadeMap.put(remoteObjectName, remoteObject);
            UnicastRemoteObject.exportObject(remoteObject);
            Naming.rebind(remoteObjectName, remoteObject);
            observer.onRegistryMessage("Binded " + remoteObjectName);
        } catch (Exception e) {
            log.warning(e.toString());
        }
    }

    /**
     * Shuts down the registry. Clean up operations are executed before shutting down
     */
    public void shutDown() {
        observer.onRegistryMessage("Shutting down ");

        LockManager.getInstance().cleanUp();

        try {
            Naming.unbind(Constants.POOL_RMI_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            observer.onRegistryMessage(e.getMessage());
        }

        observer.onShutDown();
    }
}

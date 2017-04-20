package suncertify.remote;

import java.rmi.RemoteException;
import java.rmi.server.Unreferenced;
import java.util.logging.Logger;

import suncertify.db.DataAdapter;
import suncertify.db.LockManager;


/**
 * A <CODE>RemoteDataFacadeImpl</CODE> object is the implementation of the <CODE>RemoteDataFacade</CODE>
 * interface. It is used as a remote object.
 *
 * A <CODE>RemoteDataFacadeImpl</CODE> extends the <CODE>DataAdapter</CODE>, but is used as a remote object
 * and exposes some more methods.
 * This class acts primarily as a wrapper. Business Methods
 *  (book , filter, read) are exposed to the client
 *
 * @author Diego Amicabile
 */
public class RemoteDataFacadeImpl extends DataAdapter
    implements RemoteDataFacade, Unreferenced {
    private static Logger log = Logger.getLogger("suncertify.remote");
    private static int NEXT_ID = 1;
    private String name = null;
    private RemoteDataFacadePool pool = null;
    private int id = NEXT_ID++;
    private int count = 0;

    /**
     * The RemoteDataFacadeImpl constructor
     * @param newName the name that is asociated with this remote object
     * @param newPool the pool that contains this instance of RemoteDataFacadeImpl
     */
    public RemoteDataFacadeImpl(String newName, RemoteDataFacadePool newPool) {
        pool = newPool;
        name = newName;
    }

    public int hashCode() {
        return id;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof RemoteDataFacadeImpl)) {
            return false;
        }

        if (id == ((RemoteDataFacadeImpl) obj).id) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "RemoteDataFacadeImpl(" + id + "," + count + ")";
    }

    /**
     * Cleanup operations that need to be performed when
     * removing from pool
     *
     */
    public void onRemovingFromPool() {
        LockManager.getInstance().removeLocksFor(dbMain);
    }

    /**
     * Cleans up when this instance is dereferenced from the RMI container, and removes this
     * instance from the pool
     * @throws RemoteException
     */
    public void unreferenced() {
        try {
            pool.releaseDataFacade(name);
        } catch (RemoteException e) {
            Logger.getLogger("suncertify.remote").warning("Could not release" +
                name);
        }
    }
}

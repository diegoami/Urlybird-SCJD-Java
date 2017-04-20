package suncertify.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * Pool of <CODE>RemoteDataFacadePool</CODE> which contains a pool of <CODE>RemoteDataFacade</CODE> whose instances are
 * retrieved when needed
 * @author Diego Amicabile
 *
 **/
public interface RemoteDataFacadePool extends Remote {
    /**
     * Register an instance of <CODE>RemoteDataFacade</CODE> with the RMI server and returns
     * the name which can be used to reference it
     * @return The RMI Object name
     * @throws RemoteException
     */
    public String registerDataFacade() throws RemoteException;

    /**
     * Unregister a <CODE>RemoteDataFacade</CODE> instance
     * @param remoteObjectName The RMI Object name
     * @throws RemoteException
     */
    public void releaseDataFacade(String remoteObjectName)
        throws RemoteException;
}

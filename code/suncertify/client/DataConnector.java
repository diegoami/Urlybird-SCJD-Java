package suncertify.client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.logging.Logger;

import suncertify.common.Constants;
import suncertify.common.DataFacade;
import suncertify.db.DataAdapter;
import suncertify.remote.RemoteDataFacade;
import suncertify.remote.RemoteDataFacadePool;


/**
 * Contains static methods used to retrieve a local <CODE>DataFacade</CODE>
 * or a remote connection to an <CODE>RemoteDataFacade</CODE> and <CODE>RemoteFacadePool</CODE>
 * @author Diego Amicabile
 **/
class DataConnector {
    private static Logger log = Logger.getLogger("suncertify.client");
    
    /**
     * Retrieves a connection to the <CODE>RemoteDataFacade</CODE> remote object
     * @param ip The server's Ip address
     * @param string the lookup name of a <CODE>RemoteDataFacade</CODE> instance on the  server
     * @return remote connection to a <CODE>RemoteDataFacade</CODE>
     * @throws RemoteException
     */
    
    
    static DataFacade getRemoteDataFacade(String ip, String string) throws RemoteException {
        try {
			log.info("DataConnector is getting a reference to " +
                string + " from " + ip);

            return (RemoteDataFacade) Naming.lookup("rmi://" + ip + "/" +
                string);
        } catch (Exception e) {
            System.err.println("An exception in data connector: " +
                e.getMessage());
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Retrieves a connection to the <CODE>RemoteFacadePool</CODE> remote object
     * @param ip The server's Ip address
     * @return remote connection to the <CODE>RemoteFacadePool</CODE>
     * @throws RemoteException
     */
    static RemoteDataFacadePool getRemotePool(String ip) throws RemoteException {
        try {
			log.info("DataConnector is getting Pool from " +
                ip);

            return (RemoteDataFacadePool) Naming.lookup("rmi://" + ip + "/" +
                Constants.POOL_RMI_NAME);
        } catch (Exception e) {
 
            System.err.println("An exception in getRemotePool: " +
                e.getMessage());
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Retrieves a local instance of <CODE>DataFacade</CODE>
     * @return local instance of <CODE>DataAdapter</CODE>
     */
    static DataFacade getLocal() {
        try {
            DataFacade dataFacade = new DataAdapter();

            return dataFacade;
        } catch (Exception e) {
            System.err.println("Can't get a local connection " +
                e.getMessage());
            System.exit(-1);

            return null;
        }
    }
}

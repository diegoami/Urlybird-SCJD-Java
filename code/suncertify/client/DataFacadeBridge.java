package suncertify.client;

import java.rmi.RemoteException;
import java.util.logging.Logger;

import suncertify.client.common.GuiUtils;
import suncertify.common.Constants;
import suncertify.common.DataFacade;
import suncertify.remote.RemoteDataFacadePool;
import suncertify.runner.PropertiesManager;


/**
 * Bridge to the correct <CODE>DataFacade</CODE> instance.
 * Retrieves, holds, encapsulates and releases a <CODE>DataFacade</CODE>, which might
 * be a local or remote object.
 *
 */
public class DataFacadeBridge {
    private static final String LOCAL_DATABASE_STRING = "local database";
    private String remoteName;
    private DataFacade dataFacade = null;
    private String connectionTitleString;
    private String connectionString = null;
    private int connectionType = Constants.NO_CONNECTION;
    
    

    /**
     * <CODE>DataFacadeBridge</CODE> Constructor
     * @param connectionType LOCAL_CONNECTION if the <CODE>DataFacade</CODE> is a local object , NETWORK_CONNECTION if it is
     * a remote object
     * @throws RemoteException
     */
    
    public DataFacadeBridge(int connectionType) throws RemoteException {
		tryToConnect(connectionType);
    }

    /**
     * The getter for the <CODE>DataFacade</CODE> instance which is wrapped inside the object
     * (might be a local or a remote object).
     * @return The DataFacade instance
     */
    
    public DataFacade getDataFacade() throws RemoteException {
        try {
            dataFacade.ping();
        } catch (Exception e) {
            Logger.getLogger("suncertify.client").warning(e.getMessage());
            doConnect(connectionType, true);

        }

        return dataFacade;
    }
    /**
     * Tries to connect to the remote / local DataFacade instance
     * @param connectionType local or networked
     * @param doQuit whether to quit if unsuccessful
     */

	private void doConnect(int connectionType, boolean doQuit) {
		try {
			tryToConnect(connectionType);
		}  catch (RemoteException e2) {
		   GuiUtils.showMessage("Cannot connect to "+PropertiesManager.getHostName());
		   if (doQuit) {
			   System.exit(-1);
		   }
		   
			
	    }
	}

    /**
     * If the application is running as a client, disconnect from the server
     * @throws RemoteException
     */
    
    public void disconnect() throws RemoteException {
        if (connectionType == Constants.NETWORK_CONNECTION) {
            RemoteDataFacadePool pool = DataConnector.getRemotePool(this.connectionString);
            pool.releaseDataFacade(remoteName);
        }
    }

    /**
     * The string which is printed as the main window title
     * @return The string which is printed as the main window title
     */
    
    String getConnectionTitleString() {
        return connectionTitleString;
    }

    /**
     * Retrieve a remote facade instance from the pool
     * @throws RemoteException
     */
    void retrieveRemoteFacade() throws RemoteException {
        try {
			RemoteDataFacadePool pool = DataConnector.getRemotePool(this.connectionString);
			remoteName = pool.registerDataFacade();
			
			dataFacade = DataConnector.getRemoteDataFacade(this.connectionString,
			        remoteName);
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
    }

    /**
     * Sets the kind of connection to the database (local or networked)
     * and connects (locally or on a network). Initializes the <CODE>DataFacade</CODE> object
     * @param connectionType kind of connection
     * @throws RemoteException
     */
    
    private boolean tryToConnect(int connectionType) throws RemoteException {
        if (connectionType == Constants.LOCAL_CONNECTION) {
            dataFacade = DataConnector.getLocal();

            this.connectionType = Constants.LOCAL_CONNECTION;
            this.connectionString = LOCAL_DATABASE_STRING;
        } else if (connectionType == Constants.NETWORK_CONNECTION) {
            this.connectionString = PropertiesManager.getHostName();
            this.connectionType = Constants.NETWORK_CONNECTION;
            retrieveRemoteFacade();
        } else {
            throw new IllegalArgumentException(
                "Invalid connection type specified");
        }

        this.connectionTitleString = "URLyBird : Connected to " +
            connectionString;

        return true;
    }
}

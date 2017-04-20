package suncertify.runner;

import java.rmi.RemoteException;
import java.util.logging.Logger;

import suncertify.client.DataFacadeBridge;
import suncertify.client.GuiController;
import suncertify.client.common.GuiUtils;
import suncertify.common.Constants;
import suncertify.common.Utils;
import suncertify.remote.DataRegistry;


/**
 * The URLyBird application Runner and starting point.
 *
 * @author Diego Amicabile
 */
public class ApplicationRunner {
    static final String LOCAL_MODE_STRING = "alone";
    static final String SERVER_MODE_STRING = "server";
    private Logger log = Logger.getLogger("suncertify.gui");
    private String networkArg;

    /**
     * The gui used to type configuration parameters and bootstrap the application
     */
    RunnerGui propertiesGui;

    /**
     * Starts the application as a server, a client or a standalone application
     * depending on the paramter
     * @param newNetworkArg the command line argument ("alone", "server" or none)
     */
    public ApplicationRunner(final String newNetworkArg) {
        networkArg = newNetworkArg;

        if (Utils.isEmptyString(networkArg)) {
            networkArg = "client";
        }

        propertiesGui = new RunnerGui("Command arg : " + networkArg, this);
        propertiesGui.show();
    }

    /**
     * Is the application supposed to run as a server ?
     * @return true if the application is supposed to run as a server
     */
    boolean isServer() {
        return SERVER_MODE_STRING.equalsIgnoreCase(networkArg);
    }

    /**
     * Is the application supposed to run as a client ?
     * @return true if the application is supposed to run as a client
     */
    boolean isClient() {
        return Utils.isEmptyString(networkArg);
    }

    /**
     * Is the application supposed to run as a stand alone ?
     * @return true if the application is supposed to run as a stand alone
     */
    boolean isStandAlone() {
        return LOCAL_MODE_STRING.equalsIgnoreCase(networkArg);
    }

    /**
     * Starts the application as a server, local standalone or a client
     *
     */
    void init() {
        try {
            if (isStandAlone()) {
                if (startController(Constants.LOCAL_CONNECTION)) {
                    propertiesGui.setVisible(false);
                }
            } else if (isServer()) {
                DataRegistry.getInstance().registerPool();
            } else {
                if (startController(Constants.NETWORK_CONNECTION)) {
                    propertiesGui.setVisible(false);
                } else {
                	propertiesGui.enableStart();
                }
            }
        } catch (Exception gce) {
            gce.printStackTrace();
            GuiUtils.handleException(gce.getMessage());

            propertiesGui.setVisible(true);
            propertiesGui.enableStart();
        }
    }

    /**
     * The main method that launches the application.
     * @param args Holds the command line inputs
     */
    public static void main(String[] args) {
        String networkArg = null;

        if (args.length > 0) {
            networkArg = args[0];
        }

        ApplicationRunner app = new ApplicationRunner(networkArg);
    }

    /**
     * Creates and starts the GuiController
     * @param mode whether it will run in alone, server or client mode
     * @return true if successful
     * @throws RemoteException
     */
    private boolean startController(int mode) {
       try {
			 DataFacadeBridge dataFacadeBridge = new DataFacadeBridge(mode);
			    GuiController guiController = new GuiController(dataFacadeBridge);
		} catch (RemoteException e) {
			GuiUtils.showMessage("Cannot connect to "+PropertiesManager.getHostName());
			return false;
		}

        return true;
    }
}

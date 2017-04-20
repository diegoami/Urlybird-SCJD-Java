package suncertify.client;

import java.rmi.RemoteException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import suncertify.client.common.ActionGroup;
import suncertify.client.common.ClientConstants;
import suncertify.client.common.GuiUtils;
import suncertify.client.view.MainWindow;
import suncertify.client.view.View;
import suncertify.common.ApplicationUtils;
import suncertify.common.DataFacade;
import suncertify.common.DefaultValidator;
import suncertify.common.Record;
import suncertify.common.Utils;
import suncertify.common.Validator;


/**
 * Controller class which executes the most important GUI operations
 * and coordinates the view, the model and the DataFacadeBridge instance
 * @author Diego Amicabile
 */

public class GuiController implements ClientConstants {
  private ActionGroup actionGroup = new ActionGroup();

    /**
     * Bridge to a DataFacade instance (local or remote) so that the details how to
     * create and reference it are transparent to this class
     */
    DataFacadeBridge dataFacadeBridge;

    /**
     * References an instance of the table model
     */
    RoomTableModel roomTableModel = new RoomTableModel();

    /**
     * References to an view implementation
     */
    View view = null;
    private String currentHotelName = "";
    private String currentCityName = "";
    private String[] currentCriteria = ApplicationUtils.getEmptyCriteria();
    private Validator validator = new DefaultValidator();
    private Logger log = Logger.getLogger("sampleproject.gui");

    /**
     * GuiController constructor. Initializes the reference to the DataFacadeBridge
     * @param newDataFacadeBridge a reference to a DataFacadeBridge
     */
    public GuiController(DataFacadeBridge newDataFacadeBridge) {
        this.dataFacadeBridge = newDataFacadeBridge;
        init();
    }

	/**
	 * Initializes the ActionGroup instance, the view, the model and creates 
	 * the references among them
	 */

    private void init() {
        actionGroup.filterAction = new FilterAction(this);
        actionGroup.bookAction = new BookAction(this);
        actionGroup.unbookAction = new UnbookAction(this);
        actionGroup.showAllAction = new ShowAllAction(this);
        actionGroup.reloadAction = new ReloadAction(this);
        actionGroup.quitAction = new QuitAction(this);

        actionGroup.listSelectionListener = new RoomListSelectionListener(this);

        roomTableModel = initModelWithAll();
        view = new MainWindow(dataFacadeBridge.getConnectionTitleString(),
                actionGroup, roomTableModel);

        refreshHotelsCities();

        this.enableBookActions();
    }

    /**
     * Method executed just before closing the client, disconnecting from
     * the server is necessary
     *
     */
    void beforeQuit() {
        try {
            dataFacadeBridge.disconnect();
        } catch (Exception e) {
            view.handleException(e);
        }

    }

    /**
     * Reloads data from the database or server 
     */

    void reload() {
        retrieveFiltered("", "", true);
		refreshHotelsCities();
    }

    /**
     * Sets the hotel and the city lists in the view, so that they can be
     * shown in the view's comboboxes
     *
     */
    
    void refreshHotelsCities() {
        if ((view != null) && (roomTableModel != null)) {
            view.setAllHotels(roomTableModel.getAllHotels());
            view.setAllCities(roomTableModel.getAllCities());
        }
    }

    /**
     * Retrieves all records and initializes the model
     * @throws GuiControllerException
     */
    
    RoomTableModel initModelWithAll() {
        RoomTableModel roomTableModel = retrieveFiltered("", "", false);

        return roomTableModel;
    }

    /**
     * Retrieves records which match filter criteria and fills the bookingTableModel instance
     * @param  newHotelName the hotel name filter
     * @param  newCityName the city name filter
     * @param refresh whether we have to refresh the server cache 
     *
     */

    RoomTableModel retrieveFiltered(String newHotelName, String newCityName, boolean refresh) {
        currentHotelName = newHotelName;
        currentCityName = newCityName;

        try {
            List recordList = getDataFacade().getRecordList(currentHotelName,
                    currentCityName, refresh);

            roomTableModel.setRecords(recordList);
            roomTableModel.fireTableDataChanged();

            return roomTableModel;
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);

            GuiUtils.handleException(e.getMessage());
        }

        return roomTableModel;
    }

    /**
     * Enables or disables the book and unbook actions depending on the booking
     * status of the selected row in the table
     */

    void enableBookActions() {
        int rowNo = view.getSelectedRow();

        if (rowNo == -1) {
            actionGroup.setBookingEnabledStatus(false);
            view.setCustomerNumber("");
            view.setStatusText(SELECT_OCCUPANCY_MESSAGE);

            return;
        }

        boolean roomBooked = roomTableModel.isBooked(rowNo);

        actionGroup.bookAction.setEnabled(!roomBooked);
        actionGroup.unbookAction.setEnabled(roomBooked);

        view.setCustomerNumber(roomTableModel.getCurrentCustomer(rowNo));

        view.setStatusText("");

        if (roomTableModel.getHoursToBookingDeadLine(rowNo) < 0) {
            view.setStatusText(BOOKING_PAST);
        } else  {
            view.setStatusText(roomTableModel.getHoursToBookingDeadLine(rowNo) + BOOKING_48_HOURS);
        }
    }

    /**
     * Makes sure the current record's booking is removed
     *
     */

    void unbookRecord(int recNo) {
        bookOperation(recNo, "");
    }

    /**
     * Books the current record using the customer number which has been
     * typed in the field in the left panel
     *
     */
    void bookRecord(int recNo, String arg) {
        if (Utils.isEmptyString(this.view.getCustomerName())) {
            view.showMessage(PLEASE_TYPE_CUSTOMERNO);
        } else {
            bookOperation(recNo, arg);
        }
    }

    private DataFacade getDataFacade() throws RemoteException {
        return dataFacadeBridge.getDataFacade();
    }

    /**
     * Subroutine common to the book / unbook operations
     * @param selectedRow which row in the table we are editing
     * @param customerNo the customer who is doing the booking, empty if it is an unbook operation
     */
    private void bookOperation(int selectedRow, String customerNo) {

        //confirms with user if the room is not available in the next 48 hours
        if (!GuiUtils.confirmForModification(roomTableModel.getHoursToBookingDeadLine(selectedRow))) {
            view.showMessage(ClientConstants.USER_CANCELLED);
        } else { // can book , go on
            Record record = null;
            int recNo = -1;

            try {
                if (selectedRow != -1) { // sends booking information to server / database
                    record = roomTableModel.getRecordAt(selectedRow);
                    recNo = record.getRecNo();

                    if (!getDataFacade().remoteBookOperation(customerNo, record)) {
                        throw new GuiControllerException(
                            "Concurrent modification in database");
                    }
                }
            } catch (Exception e) {
                view.handleException(e);
                view.setStatusText(BOOKING_CANCELLED);
            } finally {
                try {
                    // no matter what happened, shows the occupancy's current status
                    if (recNo != -1) {
                        record = getDataFacade().retrieveRecord(recNo);
                        roomTableModel.setRecordAt(selectedRow, record);
                    }

                    enableBookActions();
                } catch (Exception e) {
                    view.handleException(e);
                }
            }
        }
    }
}

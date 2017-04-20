package suncertify.client.view;

import java.util.Collection;


/**
 * This is the interface exposed to the controller and its actions that is
 * implemented by <CODE>MainWindow</CODE> and contains all methods that need to be
 * implemented by the Gui <CODE>View</CODE>. Acts as a facade to the GUI components
 * so that they can reengineered without the controller's knowledge.
 */
public interface View {
    /**
     * Sets a status text in the view
     * @param arg the status text
     */
    public abstract void setStatusText(String arg);

    /**
     * Shows the customer from the currently selected record
     * @param arg the customer from the currently selected record
     */
    public abstract void setCustomerNumber(String arg);

    /**
     * Retrieves the customer no which has been typed in the gui
     * @return the customer typed in the gui
     */
    public abstract String getCustomerName();

    /**
     * The hotel name as typed in the filter interface
     * @return The hotel name
     */
    public abstract String getHotelName();

    /**
     * The selected Row in the <CODE>JTable</CODE>
     * @return The selected row
     */
    public abstract int getSelectedRow();

    /**
     * The city name as typed in the filter interface
     * @return The cityname
     */
    public abstract String getCityName();

    /**
     * Resets filter to an empty filter
     */
    public abstract void clearFilter();

    /**
     * Notifies the user about an exception
     * @param e the exception that happened
     */
    public abstract void handleException(Exception e);

    /**
     * Prints a message to the usare
     * @param msg the message
     */
    public abstract void showMessage(String msg);

    /**
     * Sets the collection containing all selectable hotels
     * @param newHotels the collection containing all selectable hotels
     */
    public abstract void setAllHotels(Collection newHotels);

    /**
     * Sets the collection containing all selectable cities
     * @param newCities the collection containing all selectable cities
     */
    public abstract void setAllCities(Collection newCities);
}

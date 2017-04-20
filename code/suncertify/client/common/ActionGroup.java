package suncertify.client.common;

import javax.swing.Action;
import javax.swing.event.ListSelectionListener;


/**
 * Contains a reference to all actions  and listeners which are
 * used in the application
 */
public class ActionGroup {
    /**
     * The action executed when booking a room
     */
    public Action bookAction;

    /**
     * The action executed filtering the bookings
     * which have to be shown in the table
     */
    public Action filterAction;

    /**
     * The action executed when quitting
     */
    public Action quitAction;

    /**
     * The action executed when reloading data from the database or the server
     */
    public Action reloadAction;

    /**
     * The action executed when resetting filter so that all data is shown
     **/
    public Action showAllAction;

    /**
     * The action executed when removing a booking
     */
    public Action unbookAction;

    /**
     * The action executed when selecting a row in the table
     */
    public ListSelectionListener listSelectionListener;

    /**
     * Enables / Disables the book / unbook actions
     * @param enabled true if the actions need to be enabled , false otherwise
     */
    public void setBookingEnabledStatus(boolean enabled) {
        bookAction.setEnabled(enabled);
        unbookAction.setEnabled(enabled);
    }
}

package suncertify.client;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 *
 * Action performed when the user selects a room in the table
 *
 * @author Diego Amicabile
 *
 */
class RoomListSelectionListener implements ListSelectionListener {
    private GuiController controller;

    /**
     * Creates a <CODE>RoomListSelectionListener</CODE> instance and initializes its reference
     * to the controller
     * @param newGuiController the reference to the controller
     */
    RoomListSelectionListener(GuiController newGuiController) {
        controller = newGuiController;
    }

    /**
     * Called whenever the value of the selection changes. Calls the method
     * in the controller.
     * @param e the event that characterizes the change.
     *
     */
    public void valueChanged(ListSelectionEvent e) {
        controller.enableBookActions();
    }
}

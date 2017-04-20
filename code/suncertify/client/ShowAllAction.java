package suncertify.client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import suncertify.client.common.GuiUtils;


/**
 * Action executed when showing all the bookings
 *
 * @author Diego Amicabile
 *
 */
class ShowAllAction extends AbstractAction {
    private static final String ALL_ROOMS_ARE_SHOWN = "All rooms are shown";
    private GuiController controller;

    /**
     * Creates a <CODE>ShowAllAction</CODE> instance and initializes its reference
     * to the controller
     * @param newGuiController the reference to the controller
     */
    ShowAllAction(GuiController newGuiController) {
        super("Show All", GuiUtils.retrieveShowAllIcon());
        controller = newGuiController;
        putValue(SHORT_DESCRIPTION, "Show all bookings and remove filter");
        putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_A));
    }

    /**
     * Invoked when an action occurs. Calls the appropriate method in the controller.
     * @param ae the event that characterizes the change.
     *
     */
    public void actionPerformed(ActionEvent ae) {
        controller.view.clearFilter();
        controller.initModelWithAll();
        controller.view.setStatusText(ALL_ROOMS_ARE_SHOWN);
    }
}

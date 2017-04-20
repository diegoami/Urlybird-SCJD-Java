package suncertify.client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import suncertify.client.common.GuiUtils;


/**
 * Action executed when removing a booking from a room
 *
 * @author Diego Amicabile
 *
 */
class UnbookAction extends AbstractAction {
    private GuiController controller;

    /**
     * Creates a <CODE>UnbookAction</CODE> instance and initializes its reference
     * to the controller
     * @param newGuiController the reference to the controller
     */
    UnbookAction(GuiController newGuiController) {
        super("Unbook", GuiUtils.retrieveUnBookIcon());
        controller = newGuiController;
        putValue(SHORT_DESCRIPTION, "Discard booking for the selected room");
        putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_U));
    }

    /**
     * Invoked when the unbooking action occurs.
     * Calls the appropriate method in the controller.
     * @param ae the event that characterizes the change.
     *
     */
    public void actionPerformed(ActionEvent ae) {
        controller.unbookRecord(controller.view.getSelectedRow());
    }
}

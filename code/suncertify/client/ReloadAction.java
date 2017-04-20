package suncertify.client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import suncertify.client.common.GuiUtils;


/**
 * Action executed when reloading the bookings from the server / file
 *
 * @author Diego Amicabile
 *
 */
class ReloadAction extends AbstractAction {
    private static final String REFRESHED = "Refreshed bookings";
    private GuiController controller;

    /**
     * Creates a <CODE>ReloadAction</CODE> instance and initializes its reference
     * to the controller
     * @param newGuiController the reference to the controller
     */
    ReloadAction(GuiController newGuiController) {
        super("Refresh", GuiUtils.retrieveReloadIcon());
        controller = newGuiController;
        putValue(SHORT_DESCRIPTION, "Reloads all occupancies from server");
        putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_R));
    }

    /**
      * Invoked when the reload action occurs, calls the appropriate method in the controller
     * @param ae the event that characterizes the change.
     *
     */
    public void actionPerformed(ActionEvent ae) {
        controller.reload();
        controller.view.setStatusText(REFRESHED);
 
    }
}

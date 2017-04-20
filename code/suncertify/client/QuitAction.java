package suncertify.client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import suncertify.client.common.GuiUtils;


/**
 *
 * Action performed when the user requests to quit
 *
 * @author Diego Amicabile
 *
 */
public class QuitAction extends AbstractAction {
    private GuiController controller;

    /**
     * Creates a <CODE>QuitAction</CODE> instance and initializes its reference
     * to the controller
     * @param newGuiController the reference to the controller
     */
    public QuitAction(GuiController newGuiController) {
        super("Quit", GuiUtils.retrieveQuitIcon());
        controller = newGuiController;
        putValue(SHORT_DESCRIPTION, "Quit");
        putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_Q));
    }

    /**
    * Invoked when the quit action occurs, calls the appropriate method in the controller
     * @param ae the event that characterizes the change.
     *
     */
    public void actionPerformed(ActionEvent ae) {
        controller.beforeQuit();
        System.exit(-1);
    }
}

package suncertify.client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import suncertify.client.common.GuiUtils;


/**
 *
 * Action performed when the user requests to book
 *
 * @author Diego Amicabile
 *
 */
class BookAction extends AbstractAction {
    private GuiController controller;

    /**
     * Creates a <CODE>BookAction</CODE> instance and initializes its reference
     * to the controller
     * @param newGuiController the reference to the controller
     */
    BookAction(GuiController newGuiController) {
        super("Book", GuiUtils.retrieveBookIcon());
        controller = newGuiController;
        putValue(SHORT_DESCRIPTION, "Book the selected room for a customer");

        putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_B));
    }

    /**
     * Invoked when the booking action occurs.
     * Calls the appropriate method in the controller.
    * @param ae the event that characterizes the change.
     *
     */
    public void actionPerformed(ActionEvent ae) {
        controller.bookRecord(controller.view.getSelectedRow(),
            controller.view.getCustomerName());
    }
}

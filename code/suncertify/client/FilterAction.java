package suncertify.client;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import suncertify.client.common.GuiUtils;


/**
 * Action executed when filtering  for hotel and city
 *
 * @author Diego Amicabile
 *
 */
class FilterAction extends AbstractAction {
    private static final String FILTER_ROOMS_ARE_SHOWN = "Filter was applied";
    private GuiController controller;

    /**
     * Creates a <CODE>FilterAction</CODE> instance and initializes its reference
     * to the controller
     * @param newGuiController the reference to the controller
     */
    FilterAction(GuiController newGuiController) {
        super("Filter", GuiUtils.retrieveFilterIcon());
        controller = newGuiController;
        putValue(SHORT_DESCRIPTION,
            "Filter : Only bookings having matching hotels and cities are shown");
        putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_F));
    }

    /**
     * Invoked when the filtering action occurs, calls the appropriate method in the controller
     * @param ae the event that characterizes the change.
     *
     */
    public void actionPerformed(ActionEvent ae) {
        controller.retrieveFiltered(controller.view.getHotelName(),
            controller.view.getCityName(), false);
        controller.view.setStatusText(FILTER_ROOMS_ARE_SHOWN);
    }
}

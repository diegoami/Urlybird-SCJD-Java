package suncertify.client.common;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;


/**
 * Static utility methods for the user interface
 * @author Diego Amicabile
 **/
public class GuiUtils {
    /**
     * Puts a component in the center of the screen
     * @param component the component to center
     */
    static public void setToCenter(Component component) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((d.getWidth() - component.getWidth()) / 2);
        int y = (int) ((d.getHeight() - component.getHeight()) / 2);
        component.setLocation(x, y);
    }

    /**
     * Retrieves an <CODE>ImageIcon</CODE> instance given its file name in the classpath
     * @param imageFileName the string containing the image file name
     * @return The <CODE>ImageIcon</CODE> instance which has been retrieved
     */
    public static ImageIcon retrieveIcon(String imageFileName) {
        String imgLocation = imageFileName;
        java.net.URL imageURL = ClassLoader.getSystemResource(imgLocation);

        if (imageURL == null) {
            System.err.println("Resource not found: " + imgLocation);

            return null;
        } else {
            return new ImageIcon(imageURL);
        }
    }

    /**
     * Retrieves the <CODE>ImageIcon</CODE> we use for the filter action
     * @return the <CODE>ImageIcon</CODE> we use for the filter action
     */
    public static ImageIcon retrieveFilterIcon() {
        return retrieveIcon("images/FindAgain16.gif");
    }

    /**
     * Retrieves the <CODE>ImageIcon</CODE> we use for the reload action
     * @return the <CODE>ImageIcon</CODE> we use for the reload action
     */
    public static ImageIcon retrieveReloadIcon() {
        return retrieveIcon("images/Refresh16.gif");
    }

    /**
     * Retrieves the <CODE>ImageIcon</CODE> we use for the show all action
     * @return the <CODE>ImageIcon</CODE> we use for the show all action
     */
    public static ImageIcon retrieveShowAllIcon() {
        return retrieveIcon("images/New16.gif");
    }

    /**
     * Retrieves the <CODE>ImageIcon</CODE> we use for the book action
     * @return the <CODE>ImageIcon</CODE> we use for the book action
     */
    public static ImageIcon retrieveBookIcon() {
        return retrieveIcon("images/Play16.gif");
    }

    /**
     * Retrieves the <CODE>ImageIcon</CODE> we use for the unbook action
     * @return the <CODE>ImageIcon</CODE> we use for the unbook action
     */
    public static ImageIcon retrieveUnBookIcon() {
        return retrieveIcon("images/StepBack16.gif");
    }

    /**
     * Retrieves the <CODE>ImageIcon</CODE> we use for the quit action
     * @return the <CODE>ImageIcon</CODE> we use for the quit action
     */
    public static ImageIcon retrieveQuitIcon() {
        return retrieveIcon("images/Stop16.gif");
    }

    /**
     * Informs the user an error has occurred
     * @param msg the error message
     */
    public static void handleException(String msg) {
        JOptionPane alert = new JOptionPane(msg, JOptionPane.ERROR_MESSAGE,
                JOptionPane.DEFAULT_OPTION);
        JDialog dialog = alert.createDialog(null, "Alert");

        GuiUtils.setToCenter(dialog);
        dialog.show();
    }

    /**
     * Shows a message in a dialog
     * @param msg the message
     */
    public static void showMessage(String msg) {
        JOptionPane alert = new JOptionPane(msg, JOptionPane.OK_OPTION,
                JOptionPane.DEFAULT_OPTION);
        JDialog dialog = alert.createDialog(null, "Inform");

        // Center on screen
        GuiUtils.setToCenter(dialog);
        dialog.show();
    }

    /**
     * Asks for confirmation before going on
     * @param msg the message we print to ask for confirmation
     * @return true if the user confirmed, false otherwise
     */
    public static boolean askForConfirmation(String msg) {
        JOptionPane confirm = new JOptionPane(msg,
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.DEFAULT_OPTION);
        int result = JOptionPane.showConfirmDialog(null, msg, "Confirm",
                JOptionPane.YES_NO_OPTION);

        if (result == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Routine executed before booking. Confirm dialogs if the booking is
     * either in the past or is not within the next 48 hours
     * @param hoursToBookDeadline how many hours till midnight of the occupancy day
     * @return true if it is ok to go on to book / unbook, false otherwise
     */
    public static boolean confirmForModification(long hoursToBookDeadline) {
        boolean willPersist = true;

        if ((hoursToBookDeadline < 0)) {
            if (!GuiUtils.askForConfirmation(
                        ClientConstants.CONFIRM_BOOKING_PAST)) {
                willPersist = false;
            }
        } else if (hoursToBookDeadline >= ClientConstants.MAX_HOURS ) {
            if (!GuiUtils.askForConfirmation(
                        hoursToBookDeadline+ClientConstants.CONFIRM_BOOKING_48_HOURS)) {
                willPersist = false;
            }
        }

        return willPersist;
    }
}

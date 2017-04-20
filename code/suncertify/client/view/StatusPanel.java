package suncertify.client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


/**
 * Panel showing the current status, at the bottom
 *
 * @author Diego Amicabile
 */
public class StatusPanel extends JPanel {
    private JLabel statusLabel;

    /**
     * The status panel constructor
     */
    public StatusPanel() {
        super(new BorderLayout());
        statusLabel = new JLabel();
        this.setPreferredSize(new Dimension(500, 20));
        this.statusLabel.setHorizontalAlignment(SwingConstants.LEFT);
        this.add(statusLabel, BorderLayout.WEST);
    }

    /**
     * Sets the status text
     * @param newStatusText the status text
     */
    public void setStatusText(String newStatusText) {
        statusLabel.setText(newStatusText);
    }
}

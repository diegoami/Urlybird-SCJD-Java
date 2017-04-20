package suncertify.runner;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;


/**
 *
 * Panel used by the application when running as a server the to print  out the events
 * @author Diego Amicabile
 */
class TextPanel extends JPanel {
    JTextArea textArea = new JTextArea(12, 32);

    /**
     * TextPanel constructor
     *
     */
    TextPanel() {
        JScrollPane scrollPane = new JScrollPane(textArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.add(scrollPane);
        textArea.setEditable(false);

        TitledBorder title;
        title = BorderFactory.createTitledBorder("Server Messages");
        scrollPane.setBorder(title);
    }
}

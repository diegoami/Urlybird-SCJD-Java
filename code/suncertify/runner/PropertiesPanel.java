package suncertify.runner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;


/**
 * This panel contains configuration information that the user can change (host and filename)
 *
 * @author Diego Amicabile
 *
 */
class PropertiesPanel extends JPanel {
    JLabel fileNameLabel;
    JTextField fileNameTextField;
    JLabel hostLabel;
    JTextField hostTextField;
    JButton setButton;
    JButton resetButton;
    JButton stopButton;

    /**
     * PropertiesPanel constructor
     * @param applicationRunner reference to the main runner object
     */
    PropertiesPanel(ApplicationRunner applicationRunner) {
        super();

        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        fileNameLabel = new JLabel("File Name");
        fileNameTextField = new JTextField("", 10);
        add(fileNameLabel);
        add(fileNameTextField);

        hostLabel = new JLabel("Host name");
        hostTextField = new JTextField("", 10);
        add(hostLabel);
        add(hostTextField);

        setButton = new JButton();
        setButton.setText("Start");
        add(setButton);

        resetButton = new JButton();
        resetButton.setText("Clear");
        add(resetButton);

        stopButton = new JButton();
        stopButton.setText("Stop");
        add(stopButton);

        if (applicationRunner.isStandAlone()) {
            hostLabel.setVisible(false);
            hostTextField.setVisible(false);
            stopButton.setVisible(false);
        } else if (applicationRunner.isServer()) {
            hostLabel.setVisible(false);

            hostTextField.setVisible(false);
        } else {
            fileNameLabel.setVisible(false);

            fileNameTextField.setVisible(false);

            stopButton.setVisible(false);
        }

        layout.putConstraint(SpringLayout.WEST, fileNameLabel, 5,
            SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, fileNameLabel, 5,
            SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, hostLabel, 5,
            SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, hostLabel, 15,
            SpringLayout.SOUTH, fileNameLabel);

        layout.putConstraint(SpringLayout.WEST, fileNameTextField, 80,
            SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, fileNameTextField, 5,
            SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, hostTextField, 80,
            SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, hostTextField, 15,
            SpringLayout.SOUTH, fileNameTextField);

        layout.putConstraint(SpringLayout.WEST, setButton, 5,
            SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, setButton, 15,
            SpringLayout.SOUTH, hostLabel);

        layout.putConstraint(SpringLayout.WEST, resetButton, 75,
            SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, resetButton, 15,
            SpringLayout.SOUTH, hostLabel);

        layout.putConstraint(SpringLayout.WEST, stopButton, 145,
            SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, stopButton, 15,
            SpringLayout.SOUTH, hostLabel);

        TitledBorder title;
        title = BorderFactory.createTitledBorder("Configure properties");
        this.setBorder(title);
    }
}

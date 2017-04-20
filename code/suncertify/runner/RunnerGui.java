package suncertify.runner;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JSplitPane;

import suncertify.client.common.GuiUtils;
import suncertify.remote.DataRegistry;
import suncertify.remote.DataRegistryObserver;


/**
 * Gui which is shown to the user right after starting the application. Will query the user for configuration
 * parameters and close, or keep running and notify user about server events if running as a server
 *
 * @author Diego Amicabile
 **/
class RunnerGui extends JFrame implements DataRegistryObserver {
    private final static String newline = "\n";
    private PropertiesPanel propertiesPanel;
    private ApplicationRunner applicationRunner;
    private String totalText = "";
    private TextPanel messageTextPanel = new TextPanel();
    private boolean serverIsRunning = false;
    private Logger log = Logger.getLogger("suncertify.runner");

    /**
     * RunnerGui constructor
     * @param title the JFrame's title
     * @param newApplicationRunner reference to an applicationRunner instance
     */
    RunnerGui(String title, ApplicationRunner newApplicationRunner) {
        super(title);
        applicationRunner = newApplicationRunner;
        setupGui();

        GuiUtils.setToCenter(this);
        this.setVisible(true);
        PropertiesManager.tryToloadProperties();
        initPropertiesPanel();

        if (applicationRunner.isServer()) {
            RunnerGui.this.addWindowListener(new WindowAdapter() {
					
                    public void windowClosing(WindowEvent we) {
                        quit();
                    }
                });
        }
    }

    public void onRegistryMessage(String message) {
        totalText += (message + newline);
        log.info(message);
        messageTextPanel.textArea.append(message + newline);
    }

    public void onShutDown() {
        System.exit(1);
    }

    /**
     * Enables the start button
     */
    void enableStart() {
        propertiesPanel.setButton.setEnabled(true);
    }

    /**
     * Sets up the widgets in the frame. A splitted pane with a PropertiesPanel and a TextPanel if running
     * as a server, otherwise just a PropertiesPanel
     *
     */
    private void setupGui() {
        propertiesPanel = new PropertiesPanel(applicationRunner);

        if (applicationRunner.isServer()) {
            JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                    propertiesPanel, messageTextPanel);
            splitPane.setOneTouchExpandable(true);
            splitPane.setDividerLocation(250);

            Dimension minimumTopSize = new Dimension(100, 100);
            propertiesPanel.setMinimumSize(minimumTopSize);
            messageTextPanel.setMinimumSize(minimumTopSize);
            this.getContentPane().add(splitPane);
        } else {
            this.getContentPane().add(propertiesPanel);
        }

        this.pack();

        if (applicationRunner.isServer()) {
            this.setSize(400, 550);
        } else {
            this.setSize(400, 250);
        }
    }

    /**
     * Initializes the property panel. Associates events with the buttons
     *
     */
    private void initPropertiesPanel() {
        propertiesPanel.fileNameTextField.setText(PropertiesManager.getFileName());
        propertiesPanel.hostTextField.setText(PropertiesManager.getHostName());
        propertiesPanel.setButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    try {
                        PropertiesManager.setFileName(propertiesPanel.fileNameTextField.getText());
                        PropertiesManager.setHostName(propertiesPanel.hostTextField.getText());
                        PropertiesManager.saveProperties();
                        DataRegistry.getInstance().setObserver(RunnerGui.this);
                        propertiesPanel.setButton.setEnabled(false);

                        applicationRunner.init();
                    } catch (Exception e) {
                        GuiUtils.handleException(e.getMessage());
                        if (log.isLoggable(Level.FINE))
                        	e.printStackTrace();
                    }
                }
            });
        propertiesPanel.resetButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    propertiesPanel.fileNameTextField.setText("");
                    propertiesPanel.hostTextField.setText("");
                }
            });

        propertiesPanel.stopButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    quit();
                }
            });
    }

    /**
     * clean up operation
     *
     */
    private void quit() {
        log.info("Quitting...");

        try {
            if (serverIsRunning) {
                DataRegistry.getInstance().shutDown();
            } else {
                System.exit(1);
            }
        } catch (Exception e) {
            GuiUtils.handleException(e.getMessage());
            e.printStackTrace();
        }
    }
}

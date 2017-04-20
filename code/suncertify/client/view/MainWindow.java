package suncertify.client.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.table.TableModel;

import suncertify.client.common.ActionGroup;
import suncertify.client.common.GuiUtils;


/**
 * The main application window containing a menu, a toolbar,
 * a <CODE>TablePanel</CODE> on the right with a <CODE>JTable</CODE> in a <CODE>JScrollView</CODE>,
 * a <CODE>FilterPanel</CODE> on left top to filter data, a <CODE>BookPanel</CODE> on
 * left bottom to book / unbook customers, two splitters to separate
 * the panels and a <CODE>StatusPanel</CODE> at the bottom.
 *
 * @author Diego Amicabile
 *
 */
public class MainWindow extends JFrame implements View {
    private static final int DEFAULT_HEIGHT = 500;
    private static final int DEFAULT_WIDTH = 1000;
    private static final int DEFAULT_MAIN_DIVIDER_LOCATION = 220;
    private static final int DEFAULT_LEFT_DIVIDER_LOCATION = 170;
    private static final int DEFAULT_LEFT_POSITION = 1;
    private static final int DEFAULT_TOP_POSITION = 1;
    private TablePanel tablePanel;
    private BookPanel bookPanel;
    private FilterPanel filterPanel;
    private StatusPanel statusPanel;
    private ActionGroup actionGroup;
    private int currentHeight = DEFAULT_HEIGHT;
    private int currentWidth = DEFAULT_WIDTH;
    private int mainDividerLocation = DEFAULT_MAIN_DIVIDER_LOCATION;
    private int leftDividerLocation = DEFAULT_LEFT_DIVIDER_LOCATION;
    private int currentLeftPosition = DEFAULT_LEFT_POSITION;
    private int currentTopPosition = DEFAULT_TOP_POSITION;
    private Collection allHotels;
    private Collection allCities;
    private JSplitPane mainSplitPane;
    private JSplitPane leftSplitPane;
    private JMenuItem filterRoomsMenuItem;
    private JMenuItem showAllRoomsMenuItem;
    private JMenuItem bookRoomMenuItem;
    private JMenuItem unbookRoomMenuItem;
    private JMenuItem reloadMenuItem;
    private JMenuItem quitMenuItem;
    private JToolBar toolBar = new JToolBar();

    /**
     * Builds a main window with the specified window title.
     * @param title A String used for the main window title.
     * @param newActionGroup the <CODE>ActionGroup</CODE> instance containing all needed actions
     * @param tableModel the table model used by the <CODE>JTable</CODE>in one of the subpanels
     */
    public MainWindow(String title, ActionGroup newActionGroup,
        TableModel tableModel) {
        super(title);
        this.actionGroup = newActionGroup;

        tablePanel = new TablePanel(tableModel,
                actionGroup.listSelectionListener);
        filterPanel = new FilterPanel(actionGroup.filterAction,
                actionGroup.showAllAction);
        bookPanel = new BookPanel(actionGroup.bookAction,
                actionGroup.unbookAction);
        statusPanel = new StatusPanel();
        this.init();
        addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    actionGroup.quitAction.actionPerformed(new ActionEvent(
                            this, ActionEvent.ACTION_PERFORMED, "Quitting"));
                }
            });
    }

    private void createSplitPanes() {
        leftSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, filterPanel,
                bookPanel);
        leftSplitPane.setOneTouchExpandable(true);
        leftSplitPane.setDividerLocation(leftDividerLocation);

        Dimension minimumTopSize = new Dimension(100, 100);
        filterPanel.setMinimumSize(minimumTopSize);
        bookPanel.setMinimumSize(minimumTopSize);

        mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                leftSplitPane, tablePanel);
        mainSplitPane.setOneTouchExpandable(true);
        mainSplitPane.setDividerLocation(mainDividerLocation);

        Dimension minimumVerticalSize = new Dimension(200, 100);
        leftSplitPane.setMinimumSize(minimumVerticalSize);
        tablePanel.setMinimumSize(minimumVerticalSize);
    }

    public void setStatusText(String arg) {
        this.statusPanel.setStatusText(arg);
    }

    public void setCustomerNumber(String arg) {
        this.bookPanel.setCustomerName(arg);
    }

    public String getCustomerName() {
        return this.bookPanel.getCustomerNumber();
    }

    public String getHotelName() {
        return this.filterPanel.getSelectedHotel();
    }

    public String getCityName() {
        return this.filterPanel.getSelectedCity();
    }

    public int getSelectedRow() {
        return this.tablePanel.getSelectedRow();
    }

    public void clearFilter() {
        this.filterPanel.clearTextFields();
    }

    /**
     * Initialization method called right after the object has been constructed
     *
     */
    public void init() {
        createMenu();
        createSplitPanes();
        createPane();
        createToolBar();
        setupFrame();
    }

    public void handleException(Exception e) {
        setStatusText(e.getMessage());
        GuiUtils.handleException(e.getMessage());
    }

    /**
     * Shows a message to the user informing him about what has gone wrong
     * @param msg the message to print
     */
    public void showMessage(String msg) {
        setStatusText(msg);
        GuiUtils.showMessage(msg);
    }

    public void setAllCities(Collection collection) {
        filterPanel.setAllCities(collection);
    }

    public void setAllHotels(Collection collection) {
        filterPanel.setAllHotels(collection);
    }

    private void setupFrame() {
        this.pack();
        this.setSize(currentWidth, currentHeight);
        this.setLocation(currentLeftPosition, currentTopPosition);
        this.setVisible(true);
    }

    private void createPane() {
        this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
        this.getContentPane().add(mainSplitPane, BorderLayout.CENTER);
        this.getContentPane().add(statusPanel, BorderLayout.SOUTH);
    }

    private void createToolBar() {
        toolBar.add(new JButton(filterPanel.filterAction));
        toolBar.add(new JButton(filterPanel.showAllAction));
        toolBar.add(new JButton(bookPanel.bookAction));
        toolBar.add(new JButton(bookPanel.unbookAction));
        toolBar.add(new JButton(actionGroup.reloadAction));
        toolBar.add(new JButton(actionGroup.quitAction));

        Component[] components = toolBar.getComponents();

        for (int i = 0; i < components.length; i++) {
            ((JButton) components[i]).setText("");
        }
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu viewMenu = new JMenu("View");

        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        viewMenu.setMnemonic(KeyEvent.VK_V);
        menuBar.add(viewMenu);

        filterRoomsMenuItem = new JMenuItem(this.filterPanel.filterAction);
        showAllRoomsMenuItem = new JMenuItem(this.filterPanel.showAllAction);

        fileMenu.add(filterRoomsMenuItem);
        fileMenu.add(showAllRoomsMenuItem);

        fileMenu.addSeparator();

        bookRoomMenuItem = new JMenuItem(this.bookPanel.bookAction);
        unbookRoomMenuItem = new JMenuItem(this.bookPanel.unbookAction);

        fileMenu.add(bookRoomMenuItem);
        fileMenu.add(unbookRoomMenuItem);

        fileMenu.addSeparator();

        quitMenuItem = new JMenuItem(actionGroup.quitAction);
        fileMenu.add(quitMenuItem);

        reloadMenuItem = new JMenuItem(actionGroup.reloadAction);

        viewMenu.add(reloadMenuItem);

        this.setJMenuBar(menuBar);
    }
}

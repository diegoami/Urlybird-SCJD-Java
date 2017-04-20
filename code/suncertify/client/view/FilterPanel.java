package suncertify.client.view;

import java.util.Collection;
import java.util.Iterator;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;


/**
 * Panel for filtering out the records which need to be shown in the table
 * @author Diego Amicabile
 */
public class FilterPanel extends JPanel {
    private static final String FILTER_TITLE = "Filter";
    private static final String LOCATION_TEXT = "City: ";
    private static final String NAME_TEXT = "Hotel: ";
    private JLabel hotelLabel;
    private JComboBox hotelComboBox;
    private JLabel cityLabel;
    private JComboBox cityComboBox;
    private JButton filterButton;
    private JButton showAllButton;

    /**
     * The action executed when the user request to activate a filter
     */
    Action filterAction;

    /**
     * The action executed when the user requests to see all rooms
     */
    Action showAllAction;

    /**
     * The Filter Panel Constuctor
     * @param newFilterAction the action associated with the filter button
     * @param newShowAllAction the action associated with the show all button
     */
    public FilterPanel(Action newFilterAction, Action newShowAllAction) {
        filterAction = newFilterAction;
        showAllAction = newShowAllAction;

        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        //Create and add the components.
        hotelLabel = new JLabel(NAME_TEXT);
        hotelComboBox = new JComboBox();
        hotelComboBox.setSize(100, 10);
        add(hotelComboBox);

        cityLabel = new JLabel(LOCATION_TEXT);
        cityComboBox = new JComboBox();
        cityComboBox.setSize(100, 10);

        add(cityComboBox);

        filterButton = new JButton(filterAction);
        add(filterButton);

        showAllButton = new JButton(showAllAction);

        add(showAllButton);
        layout.putConstraint(SpringLayout.WEST, hotelComboBox, 5,
            SpringLayout.WEST, this);

        layout.putConstraint(SpringLayout.NORTH, hotelComboBox, 5,
            SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, cityComboBox, 5,
            SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, cityComboBox, 15,
            SpringLayout.SOUTH, hotelComboBox);

        layout.putConstraint(SpringLayout.WEST, filterButton, 5,
            SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, filterButton, 35,
            SpringLayout.SOUTH, cityComboBox);

        layout.putConstraint(SpringLayout.WEST, showAllButton, 95,
            SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, showAllButton, 35,
            SpringLayout.SOUTH, cityComboBox);

        TitledBorder title;
        title = BorderFactory.createTitledBorder(FILTER_TITLE);
        this.setBorder(title);
    }

    /**
     * The selected hotel in the hotel combo box which is used to filter
     * @return The selected hotel in the hotel combo box
     */
    public String getSelectedHotel() {
        if (hotelComboBox.getSelectedIndex() > 0) {
            return (String) hotelComboBox.getSelectedItem();
        } else {
            return "";
        }
    }

    /**
     * The selected city in the city combo box which is used to filter
     * @return The selected city in the city combo box
     */
    public String getSelectedCity() {
        if (cityComboBox.getSelectedIndex() > 0) {
            return (String) cityComboBox.getSelectedItem();
        } else {
            return "";
        }
    }

    /**
     * Clears name and location text fields
     *
     */
    public void clearTextFields() {
        hotelComboBox.setSelectedIndex(0);
        cityComboBox.setSelectedIndex(0);
    }

    private void setComboboxObject(JComboBox comboBox, Collection collection,
        String pleaseChooseString) {
        comboBox.removeAllItems();
        comboBox.addItem(pleaseChooseString);

        for (Iterator iter = collection.iterator(); iter.hasNext();) {
            comboBox.addItem(iter.next());
        }
    }

    /**
     * Initializes the city combobox
     * @param newCities a collection containing all Strings where a String is a city
     */
    void setAllCities(Collection newCities) {
        setComboboxObject(cityComboBox, newCities, "Select a City...");
    }

    /**
     * Initializes the hotel combobox
     * @param newHotels a collection containing all Strings where a String is a hotel
     */
    void setAllHotels(Collection newHotels) {
        setComboboxObject(hotelComboBox, newHotels, "Select a hotel...");
    }
}

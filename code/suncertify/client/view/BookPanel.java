package suncertify.client.view;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.TitledBorder;


/**
 * Panel where you can type a customer no and booking the currently selected record, or unbook it
 * @author Diego Amicabile
 */
public class BookPanel extends JPanel {
    private static final String BOOK_CONSTANT = "Book";
    private static final String CUSTOMER_STRING = "Customer: ";
    private JLabel customerLabel;
    private JTextField customerTextField;
    private JButton bookButton;
    private JButton unbookButton;

    /**
     * Action performed when clicking on the book button
     */
    Action bookAction;

    /**
     * Action performed when clicking on the unbook button
     */
    Action unbookAction;

    /**
     * The booking panel constructor
     * @param newBookAction the action to perform when clicking on the booking button
     * @param newUnbookAction the action to perform when clicking on the unbooking button
     */
    public BookPanel(Action newBookAction, Action newUnbookAction) {
        bookAction = newBookAction;
        unbookAction = newUnbookAction;

        SpringLayout layout = new SpringLayout();
        setLayout(layout);

        customerLabel = new JLabel(CUSTOMER_STRING);
        customerTextField = new JTextField("", 10);
        add(customerLabel);
        add(customerTextField);

        bookButton = new JButton(bookAction);
        add(bookButton);

        unbookButton = new JButton(unbookAction);
        add(unbookButton);

        layout.putConstraint(SpringLayout.WEST, customerLabel, 5,
            SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, customerLabel, 5,
            SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, bookButton, 5,
            SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, bookButton, 45,
            SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, customerTextField, 75,
            SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, customerTextField, 5,
            SpringLayout.NORTH, this);

        layout.putConstraint(SpringLayout.WEST, unbookButton, 95,
            SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.NORTH, unbookButton, 45,
            SpringLayout.NORTH, this);

        TitledBorder title;
        title = BorderFactory.createTitledBorder(BOOK_CONSTANT);
        this.setBorder(title);
    }

    /**
     * Getter for the customer number which has been typed in the panel's textfield
     * @return The panel's textfield content which is the customer number
     */
    public String getCustomerNumber() {
        return this.customerTextField.getText();
    }

    /**
     * Sets the customer number in the panel's textfield
     * @param value The value we need to set the panel's textfield to
     */
    public void setCustomerName(String value) {
        this.customerTextField.setText(value);
    }
}

package suncertify.client;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.table.AbstractTableModel;

import suncertify.common.AlreadyBookedException;
import suncertify.common.Constants;
import suncertify.common.IllegalRecordException;
import suncertify.common.Record;


/**
 * Table model containing information about the rooms and the bookings
 * @author Diego Amicabile
 */
class RoomTableModel extends AbstractTableModel {
    private static final short HOURS_BEFORE = 40;
    private final static String[] COLUMN_NAMES = {
        "Hotel Name", "City", "Max Occupancy", "Smoking", "Price",
        "Availability", "Customer"
    };
    private Logger log = Logger.getLogger("suncertify.gui");
    private String[] headerNames = COLUMN_NAMES;
    private List roomRecords = new ArrayList();

    /**
     * Returns the column count of the table.
     *
     * @return An integer indicating the number or columns in the table.
     */
    public int getColumnCount() {
        return this.headerNames.length;
    }

    /**
     * Returns the number of rows in the table.
     *
     * @return An integer indicating the number of rows in the table.
     */
    public int getRowCount() {
        return this.roomRecords.size();
    }

    /**
     * Gets a value from a specified index in the table.
     *
     * @param row An integer representing the row index.
     * @param column An integer representing the column index.
     * @return The object located at the specified row and column.
     */
    public Object getValueAt(int row, int column) {
        Object[] renderData = (Object[]) ((Record) this.roomRecords.get(row)).getRenderData();
        Object result = renderData[column];

        return renderData[column];
    }

    /**
     * Sets the cell value at a specified index.
     *
     * @param obj The object that is placed in the table cell.
     * @param row The row index.
     * @param column The column index.
     */
    public void setValueAt(Object obj, int row, int column) {
        Object[] temp = (Object[]) ((Record) this.roomRecords.get(row)).getRenderData();
        temp[column] = obj;
    }

    /**
     * This method returns the right class for a table column so that the
     * right renderer is produced
     * @param c the column index
     * @return the classes whose render we want to show
     */
    public Class getColumnClass(int c) {
        if (getValueAt(0, c) != null) {
            return getValueAt(0, c).getClass();
        } else {
            return Object.class;
        }
    }

    /**
     * Returns the name of a column at a given column index.
     *
     * @param column The specified column index.
     * @return A String containing the column name.
     */
    public String getColumnName(int column) {
        return headerNames[column];
    }

    /**
     * Given a row and column index, indicates if a table cell can be edited.
     *
     * @param row Specified row index.
     * @param column Specified column index.
     * @return A boolean indicating whether a cell is editable.
     */
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    /**
     * Retrieves the record which is associated to a row in the table
     * @param rowNo index of the record in the table model
     * @return Array of strings containing the actual data
     */
    Record getRecordAt(int rowNo) {
        return (Record) this.roomRecords.get(rowNo);
    }

    /**
     * Inserts a record into a row in the table
     * @param rowNo index of the record in the table model
     * @param record the record we are inserting into the table model
     */
    void setRecordAt(int rowNo, Record record) {
        this.roomRecords.set(rowNo, record);
        this.fireTableRowsUpdated(rowNo, rowNo);
    }

    /**
     * Sets all the records in the table model (array of record)
     * @param newRecords All the records which are inserted
     */
    void setRecords(List newRecords) {
        this.roomRecords.clear();
        this.roomRecords.addAll(newRecords);
    }

    /**
     * All hotels in the model
     * @return list containing All hotels in the model
     */
    List getAllHotels() {
        List allHotels = new ArrayList();

        for (int i = 0; i < this.getRowCount(); i++) {
            Record record = this.getRecordAt(i);

            if (!allHotels.contains(record.getHotel().trim())) {
                allHotels.add(record.getHotel().trim());
            }
        }

        java.util.Collections.sort(allHotels);

        return allHotels;
    }

    /**
     * All cities in the model
     * @return list containing All hotels in the model
     */
    List getAllCities() {
        List allCities = new ArrayList();

        for (int i = 0; i < this.getRowCount(); i++) {
            Record record = this.getRecordAt(i);

            if (!allCities.contains(record.getCity().trim())) {
                allCities.add(record.getCity().trim());
            }
        }

        java.util.Collections.sort(allCities);

        return allCities;
    }

    /**
     * Tries to book a record
     * @param rowNo the position of the record in the table model which we are trying to book
     * @param customerNo the customer which is booking the record
     * @throws AlreadyBookedException
     */
    void bookRecord(int rowNo, String customerNo) throws AlreadyBookedException {
        Record record = this.getRecordAt(rowNo);
        record.book(customerNo);
    }

    /**
     * Checks whether a record is booked
     * @param rowNo the position of the record in the table model which we are trying to book
     * @return true whether it is booked, false otherwise
     */
    boolean isBooked(int rowNo) {
        Record record = this.getRecordAt(rowNo);

        return record.isBooked();
    }

    /**
     * Tries to remove a booking for a record
     * @param rowNo the position of the record in the table model which we are trying to unbook
     */
    void unbookRecord(int rowNo) {
        Record record = this.getRecordAt(rowNo);
        record.unBook();
    }

    /**
     * Retrieves the customer number for a recor
     * @param rowNo The selected row
     * @return the customer  number
     */
    String getCurrentCustomer(int rowNo) {
        Record record = this.getRecordAt(rowNo);
        String customer = record.getData()[Constants.CUSTOMER_HOLDING_POS];

        if (customer != null) {
            customer = customer.trim();
        }

        return customer;
    }



    /**
     * Finds out how many hours are left for booking
     * @param rowNo the selected row
     * @return how many hours are left for booking
     * @throws IllegalRecordException
     */
    long getHoursToBookingDeadLine(int rowNo) throws IllegalRecordException {
        Record record = this.getRecordAt(rowNo);
        long result;

        result = record.hoursToBook();

        return result;
    }

    /**
     * removes all records from the table model
     */
    private void clear() {
        this.roomRecords.clear();
    }
}

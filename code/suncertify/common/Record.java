package suncertify.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Logger;


/**
 * Wrapper around an instance of a record in the database file. Contains the data from the file,
 * its position in the file and a deleted flag. Provides some conveniency methods to retrieve, convert
 * and manipulate its fields.
 * @author Diego Amicabile
 *
 */
public class Record implements java.io.Serializable, Cloneable {
    private static final String DATE_FORMAT = "yyyy/MM/dd";
    private static final long MILLISEC_PER_HOUR = 1000 * 60 * 60 ;
    private String[] data;
    private int recNo;
    private byte deletedByte;

    /**
     * Creates a copy of this record, which contains the actual data, the record position
     * and the deleted flag
     * @return a copy of this record
     */
    public Object clone() {
        Record record = new Record();
        record.setData((String[]) this.getData().clone());
        record.setRecNo(this.getRecNo());
        record.deletedByte = this.deletedByte;

        return record;
    }

    /**
     * Hotel name getter
     * @return The hotel name
     */
    public String getHotel() {
        return getData()[Constants.HOTEL_POS];
    }

    /**
     * City name getter
     * @return The city name
     */
    public String getCity() {
        return getData()[Constants.CITY_POS];
    }

    /**
     * Maximum occupancy getter
     * @return Maximum occupancy
     */
    public Integer getMaxOccupancy() {
        if (Utils.isEmptyString(getData()[Constants.MAX_OCCUPANCY_POS])) {
            return null;
        }

        try {
            return new Integer(getData()[Constants.MAX_OCCUPANCY_POS].trim());
        } catch (NumberFormatException e) {
            Logger.getLogger("suncertify").warning("Max Occupancy could not be started : " +
                getData()[Constants.MAX_OCCUPANCY_POS]);
        }

        return null;
    }

    /**
     * Smoking flag getter
     * @return Smoking flag
     */
    public Boolean getSmoking() {
        return new Boolean("Y".equals(getData()[Constants.SMOKING_POS]));
    }

    /**
     * Price per night getter
     * @return Price per night
     */
    public String getPricePerNight() {
        return getData()[Constants.PRICE_PER_NIGHT_POS];
    }

    /**
     * Date available getter
     * @return Date available
     */
    public Date getDateAvailable() {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

        Date date = null;

        try {
            date = format.parse(getData()[Constants.DATE_AVAILABLE_POS]);
        } catch (ParseException e) {
            Logger.getLogger("suncertify.db").warning("DateAvaible could not be parsed : " +
                date);
        }

        return date;
    }

    /**
     * Customer holding the record
     * @return Customer holding
     */
    public String getCustomerHolding() {
        if (Utils.isEmptyString(getData()[Constants.CUSTOMER_HOLDING_POS])) {
            return null;
        }

        try {
            return getData()[Constants.CUSTOMER_HOLDING_POS];
        } catch (NumberFormatException e) {
            Logger.getLogger("suncertify.db").warning("Customer Holding could not be retrieved ");
        }

        return null;
    }

    /**
     * Array of objects used by the JTable to render correctly the data
     * @return An array of objects
     */
    public Object[] getRenderData() {
        Object[] renderData = new Object[] {
                getHotel().trim(), getCity().trim(), this.getMaxOccupancy(),
                this.getSmoking(), this.getPricePerNight(),
                this.getDateAvailable(), this.getCustomerHolding()
            };

        return renderData;
    }

    /**
     * How many days there are between today and the availability date
     * @return Days left till today
     */
    public long hoursToBook() throws IllegalRecordException {
        try {
            GregorianCalendar currentCalendar = new GregorianCalendar();

            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
            GregorianCalendar dateFieldCalendar = new GregorianCalendar();

            Date date = format.parse(getData()[Constants.DATE_AVAILABLE_POS]);
            dateFieldCalendar.setTime(date);

            long dayResult = (dateFieldCalendar.getTime().getTime() -
                currentCalendar.getTime().getTime()) / MILLISEC_PER_HOUR;

            return dayResult;
        } catch (Exception e) {
            throw new IllegalRecordException(e);
        }
    }

    /**
     * Data in the record
     * @return The Data
     */
    public String[] getData() {
        if (data == null) {
            Logger.getLogger("suncertify.db").warning("Data field was null");
            data = new String[Constants.FIELD_NUM];
        }

        return data;
    }

    /**
     * Retrieves the record number (as in database)
     * @return Record number
     */
    public int getRecNo() {
        return recNo;
    }

    /**
     * Sets the data in the record
     * @param strings The value we set the strings to
     */
    public void setData(String[] strings) {
        data = strings;
    }

    /**
     * Sets the deleted Byte for the record
     * @param value The value we set the deleted Byte to
     */
    public void setDeletedByte(byte value) {
        deletedByte = value;
    }

    /**
     * Sets the deleted Byte for the record using a boolean as argument
     * @param value the logical value for the deleted byte
     */
    public void setDeleted(boolean value) {
        if (value) {
            deletedByte = (byte) 0xFF;
        } else {
            deletedByte = 0;
        }
    }

    /**
     * Checks whether the record is deleted
     * @return  Whether the record is deleted
     */
    public boolean isDeleted() {
        return deletedByte != 0;
    }

    /**
     * The Deleted Byte getter
     * @return The Deleted Byte
     */
    public byte getDeletedByte() {
        return deletedByte;
    }

    /**
     * A string version of the record, useful for debugging.
     * @return A string version of the record
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer("");

        if (isDeleted()) {
            buffer.append("Deleted Record(");
        } else {
            buffer.append("Record(");
        }

        for (int i = 0; i < getData().length; i++) {
            buffer.append(getData()[i]);

            if (i < (getData().length - 1)) {
                buffer.append(",");
            }
        }

        buffer.append(")");

        return buffer.toString();
    }

    private boolean fieldMatches(String criteriaField, String recordField) {
        boolean fieldMatches = true;

        if ((criteriaField == null) || (criteriaField.length() == 0)) {
        } else if (recordField == null) {
            fieldMatches = false;
        } else if (recordField.trim().equalsIgnoreCase(criteriaField.trim())) {
        } else {
            fieldMatches = false;
        }

        return fieldMatches;
    }

    /**
     * Checks whether the record matches the search criteria
     * @param criteria Search criteria
     * @return true Whether the record matches the search criteria
     */
    public boolean matches(String[] criteria) {
        boolean recordMatches = true;

        for (short fieldCounter = 0; fieldCounter < criteria.length;
                fieldCounter++) {
            if (!fieldMatches(criteria[fieldCounter], getData()[fieldCounter])) {
                recordMatches = false;

                break;
            }
        }

        return recordMatches;
    }

    /**
     * Checks whether a record is booked, not synchronized since this is just a Data Transfer Object
     * @return whether the record is booked
     */
    public boolean isBooked() {
        return !Utils.isEmptyString(getData()[Constants.CUSTOMER_HOLDING_POS]);
    }

    /**
     * Tries to book a record, fails if record is already booked
     * @param customerNo customer which is performing the booking
     * @throws AlreadyBookedException
     */
    public void book(String customerNo) throws AlreadyBookedException {
        if (isBooked()) {
            throw new AlreadyBookedException("Record " + this.getRecNo() +
                " is already booked");
        } else {
            getData()[Constants.CUSTOMER_HOLDING_POS] = customerNo;
        }
    }

    /**
     * tries to unbook a record
     */
    public void unBook() {
        getData()[Constants.CUSTOMER_HOLDING_POS] = "";
    }

    /**
     * Setter for the record number (position in the file)
     * @param i the record position in the file
     */
    public void setRecNo(int i) {
        recNo = i;
    }
}

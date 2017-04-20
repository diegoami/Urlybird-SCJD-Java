package suncertify.common;


/**
 * Interface containing constants used by the application
 *
 */
public interface Constants {
    /**
     * Application is running locally
     */
    public final static int LOCAL_CONNECTION = 1;

    /**
     * Application is connecting to a remote server
     */
    public final static int NETWORK_CONNECTION = 2;

    /**
     * Application is working as a server
     */
    public final static int NO_CONNECTION = 0;

    /**
     * Hotel field position in the file records
     */
    public final static short HOTEL_POS = 0;

    /**
     * Location field position in the file records
     */
    public final static short CITY_POS = 1;

    /**
     * maximum occupancy field position in the file records
     */
    public final static short MAX_OCCUPANCY_POS = 2;

    /**
     * Smoking flag position in the file records
     */
    public final static short SMOKING_POS = 3;

    /**
     * Price per night field position in the file records
     */
    public final static short PRICE_PER_NIGHT_POS = 4;

    /**
     * Date available field position in the file records
     */
    public final static short DATE_AVAILABLE_POS = 5;

    /**
     * Customer holding field position in the file records
     */
    public final static short CUSTOMER_HOLDING_POS = 6;

    /**
     * Number of fields in the file records
     */
    public final static short FIELD_NUM = 7;

    /**
     * The RemoteFacadePool's RMI name
     */
    public static final String POOL_RMI_NAME = "Pool";
}

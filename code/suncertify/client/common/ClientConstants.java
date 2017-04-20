package suncertify.client.common;


/**
 *
 *
 * This interface contains constants and strings used by the GUI
 *
 * @author Diego Amicabile
 */
public interface ClientConstants {
	/**
	 * Within how many hours is booking allowed ? (Till midnight of the available date)
	 */
	
	public static final long MAX_HOURS = 48;
    /**
     * Message printed when a the user is trying to book with an empty customer no
     */
    public static final String PLEASE_TYPE_CUSTOMERNO = "Please type a customer number";

    /**
     * Message printed when a booking is not successful
     */
    public static final String BOOKING_CANCELLED = "Booking was not successful";

    /**
     * Message printed a booking is not successful
     */
    public static final String BOOKED_ROOM = "Room has been booked";

    /**
     * Message printed when the user cancelled the operation
     */
    public static final String USER_CANCELLED = "Operation cancelled by user";

    /**
     * Message printed when the user is trying to book a room which won't be available within 48 hours
     */
    public static final String CONFIRM_BOOKING_48_HOURS = " hours to start of room occupancy . Confirm ?";

    /**
     * Message printed when the user is trying to book a room which is in the past
     */
    public static final String CONFIRM_BOOKING_PAST = "Room occupancy  is in the past. Confirm ?";

    /**
     * Message printed when the user is browsing on a booking which is in within 48 hours
     */
    public static final String BOOKING_48_HOURS = " hours to start of room occupancy";

    /**
     * Message printed when the user is browsing on a booking which is in the past
     */
    public static final String BOOKING_PAST = "Room occupancy is in the past";

    /**
     * Message printed on the title bar
     */
    public final static String TITLE = "URLyBird 1.3.3";
    
    /**
     * Message prompting the user to select an occupancy in the table
     */
    
	public static final String SELECT_OCCUPANCY_MESSAGE = "Please select an occupancy in the table";
	
}

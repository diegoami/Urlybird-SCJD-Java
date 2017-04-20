package suncertify.common;


/**
 * Utility class containing static methods useful in the application
 * @author Diego Amicabile
 *
 */
public class ApplicationUtils {
    private static final String[] emptyCriteria = new String[] {
            "", "", "", "", "", "", ""
        };

    /**
     * Creates a string array which can be used to perform a filter operation
     * @param hotel the hotel filter criteria
     * @param city the city filter criteria
     * @return the string array which can be used to filter
     */
    public static String[] getFullCriteria(String hotel, String city) {
        String[] criteria = new String[] { hotel, city, "", "", "", "", "" };

        return criteria;
    }

    /**
     * Creates a empty string array which can be used to
     * perform a filter operation which retrieves all data
     * @return the string array which can be used to retrieve all record
     */
    public static String[] getEmptyCriteria() {
        return emptyCriteria;
    }
}

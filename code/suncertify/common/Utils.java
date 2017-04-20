package suncertify.common;

import java.util.logging.Logger;


/**
 * Common utilities method
 *
 * @author Diego Amicable
 */
public class Utils {
    /**
     * Checks if two array of strings have equal elements
     * @param first first array which has to be compared
     * @param second second array which has to be compared
     * @return true if all the strings in the arrays are equal, false otherwise
     */
    public static boolean stringArrayEquals(String[] first, String[] second) {
        boolean result = true;

        for (int i = 0; i < first.length; i++) {
            if (!first[i].equals(second[i])) {
                Logger.getLogger("suncertify.db").fine(first[i] + " != " +
                    second[i]);

                result = false;

                break;
            }
        }

        return result;
    }

    /**
     * Checks whether a string is null or its content is void
     * @param arg the string to check
     * @return true if string is null or void
     */
    public static boolean isEmptyString(String arg) {
        return ((arg == null) || (arg.trim().length() == 0));
    }
}

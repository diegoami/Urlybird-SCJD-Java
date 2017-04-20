package suncertify.common;

import java.util.regex.Pattern;


/**
 *
 * Default validator for the fields in the database
 *
 * @author Diego Amicabile
 */
public class DefaultValidator implements Validator {
    private final static String DATE_EXPRESSION = "([0-9]){4}/([0-9]){2}/([0-9]){2}";
    private final static String INT_EXPRESSION = "([0-9])+";
    private final static String PRICE_EXPRESSION = "(\\$)([0-9]+)(\\.[0-9]+)?";
    private final static String YESNOFLAG_EXPRESSION = "Y|N";
    private final static String CUSTOMER_EXPRESSION = "(([0-9]){8})?";

    /**
     * Checks if the parameter is a valid date
     * @param arg the string to check
     * @return true if the argument is a valid date, false otherwise
     */
    public boolean validateDate(String arg) {
        return Pattern.matches(DATE_EXPRESSION, arg);
    }

    /**
     * Checks if the parameter can be converted to an integer
     * @param arg the string to check
     * @return true if the argument is a valid integer , false otherwise
     */
    public boolean validateCustomer(String arg) {
        return Pattern.matches(CUSTOMER_EXPRESSION, arg);
    }

    /**
     * Checks if the parameter can be converted to an integer
     * @param arg the string to check
     * @return true if the argument is a valid integer , false otherwise
     */
    public boolean validateInteger(String arg) {
        return Pattern.matches(INT_EXPRESSION, arg);
    }

    /**
     * Checks if the parameter is a valid price
     * @param arg the string to check
     * @return true if the argument is a valid price, false otherwise
     */
    public boolean validatePrice(String arg) {
        return Pattern.matches(PRICE_EXPRESSION, arg);
    }

    /**
     * Checks if the parameter is in the form Y / N
     * @param arg the string to check
     * @return true if the argument is a in the form Y/N , false otherwise
     */
    public boolean validateYesNoFlag(String arg) {
        return Pattern.matches(YESNOFLAG_EXPRESSION, arg);
    }
}

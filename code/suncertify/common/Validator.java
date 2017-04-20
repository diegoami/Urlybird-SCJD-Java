package suncertify.common;


/**
 * Interface containing the methods which need to be implemented to validate
 * any record field
 *
 * @author Diego Amicabile
 */
public interface Validator {
    /**
     * Checks if the parameter can be converted to an integer
     * @param arg the string to check
     * @return true if the argument is a valid integer , false otherwise
     */
    public boolean validateInteger(String arg);

    /**
     * Checks if the parameter is in the form Y / N
     * @param arg the string to check
     * @return true if the argument is a in the form Y/N , false otherwise
     */
    public boolean validateYesNoFlag(String arg);

    /**
     * Checks if the parameter is a valid price
     * @param arg the string to check
     * @return true if the argument is a valid price, false otherwise
     */
    public boolean validatePrice(String arg);

    /**
     * Checks if the parameter is a valid date
     * @param arg the string to check
     * @return true if the argument is a valid date, false otherwise
     */
    public boolean validateDate(String arg);

    /**
     * Checks if the parameter is a valid customer number
     * @param arg the string to check
     * @return true if the argument is a valid customer, false otherwise
     */
    public boolean validateCustomer(String arg);
}

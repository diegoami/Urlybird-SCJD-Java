package suncertify.common;



/**
 *
 * Validates a <CODE>String[]</CODE> record - checks if data is valid
 *
 *  @author Diego Amicabile
 *
 */
public class DefaultRecordValidator {
    /**
     * The delegate validator class
     */
    Validator validator = new DefaultValidator();

    /**
     * Fully validates a string array using the validating methods
     * in the class <CODE>Validator</CODE>
     * @param data the string array containing the criteria to validate
     * @return true if the criteria are valid, false otherwise
     * @throws ValidateException
     */
    public boolean validate(String[] data) throws ValidateException {
        String hotelName = data[Constants.HOTEL_POS];

        if (Utils.isEmptyString(data[Constants.HOTEL_POS])) {
            throw new ValidateException("Please specify an Hotel");
        }

        if (Utils.isEmptyString(data[Constants.CITY_POS])) {
            throw new ValidateException("Please specify a City");
        }

        if (Utils.isEmptyString(data[Constants.MAX_OCCUPANCY_POS])) {
            throw new ValidateException(
                "Please specify Maximum occupancy per room");
        }

        if (!validator.validateInteger(data[Constants.MAX_OCCUPANCY_POS].trim())) {
            throw new ValidateException(
                "Maximum occupancy per room MUST be a number");
        }

        if (Utils.isEmptyString(data[Constants.SMOKING_POS])) {
            throw new ValidateException(
                "Please specify whether you can smoke in Room (Y/N)");
        }

        if (!validator.validateYesNoFlag(data[Constants.SMOKING_POS].trim())) {
            throw new ValidateException(
                "Please specify whether you can smoke in Room (Y/N)");
        }

        if (Utils.isEmptyString(data[Constants.PRICE_PER_NIGHT_POS])) {
            throw new ValidateException("Please specify Room Price");
        }

        if (!validator.validatePrice(data[Constants.PRICE_PER_NIGHT_POS].trim())) {
            throw new ValidateException("Room Price Format must be $XXX(.XX)");
        }

        if (Utils.isEmptyString(data[Constants.DATE_AVAILABLE_POS])) {
            throw new ValidateException("Please specify Booking Date");
        }

        if (!validator.validateDate(data[Constants.DATE_AVAILABLE_POS].trim())) {
            throw new ValidateException("Booking Date fomat is YYYY/MM/DDD");
        }

        if (data[Constants.CUSTOMER_HOLDING_POS] == null) {
            throw new ValidateException("Customer was null");
        }

        if (!validator.validateCustomer(
                    data[Constants.CUSTOMER_HOLDING_POS].trim())) {
            throw new ValidateException("Customer must be a 8-digit number");
        }

        return true;
    }
}

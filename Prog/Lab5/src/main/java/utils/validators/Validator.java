package utils.validators;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that validates fields value
 */
public class Validator {

    /**
     * Method that validate fields value
     * @param value value
     * @param validators ArrayList of validators
     * @return true if all is alright
     * @param <T> field type
     * @throws IllegalArgumentException throws if value isn't valid
     */
    public static  <T> boolean validate(T value, ArrayList<Validatable> validators) throws IllegalArgumentException  {
        boolean result = true;
        for(Validatable validator : validators) {
            result = result && validator.validate(value);
        }
        return result;
    }

}
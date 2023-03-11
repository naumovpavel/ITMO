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
     * @param <T> field type
     * @throws IllegalArgumentException throws if value isn't valid
     */
    public static  <T> void validate(T value, ArrayList<Validatable> validators) throws IllegalArgumentException  {
        for(Validatable validator : validators) {
            validator.validate(value);
        }
    }

}
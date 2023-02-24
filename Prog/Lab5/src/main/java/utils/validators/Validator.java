package utils.validators;

import java.util.ArrayList;
import java.util.Scanner;

public class Validator {
    //@SafeVarargs
    public static  <T> boolean validate(T value, ArrayList<Validatable> validators) throws IllegalArgumentException  {
        boolean result = true;
        for(Validatable validator : validators) {
            result = result && validator.validate(value);
        }
        return result;
    }

}
package common.utils.validators;

import java.util.ResourceBundle;

/**
 * Class that validate that fields value isn't empty string
 */
public class StringNotNoneValidator implements Validatable {
    private static StringNotNoneValidator instance;

    public <T> boolean validate(T value) throws IllegalArgumentException {
        String s;
        try {
            s = (String) value;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(ResourceBundle.getBundle("bundles.Errors").getString("not_string_error"));
        }
        if(value == null || s.isEmpty() || s.isBlank()) {
            throw new IllegalArgumentException(ResourceBundle.getBundle("bundles.Errors").getString("empty_string_error"));
        }
        return true;
    }

    @Override
    public Validatable getInstance() {
        if (instance == null) {
            instance = new StringNotNoneValidator();
        }
        return instance;
    }
}

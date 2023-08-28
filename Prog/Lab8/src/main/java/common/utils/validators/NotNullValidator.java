package common.utils.validators;

import java.util.ResourceBundle;

/**
 * Class that validate that fields value isn't null
 */
public class NotNullValidator implements Validatable {
    private static NotNullValidator instance;

    public <T> boolean validate(T value) throws IllegalArgumentException{
        if(value == null) {
            throw new IllegalArgumentException(ResourceBundle.getBundle("bundles.Errors").getString("null_error"));
        }
        return true;
    }

    public NotNullValidator getInstance() {
        if(instance == null) {
            instance = new NotNullValidator();
        }
        return instance;
    }
}

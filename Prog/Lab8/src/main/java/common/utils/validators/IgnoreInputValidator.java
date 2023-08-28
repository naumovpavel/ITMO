package common.utils.validators;

/**
 * Class that validate that fields value is ignored in input
 */
public class IgnoreInputValidator implements Validatable {
    private static IgnoreInputValidator instance;

    public <T> boolean validate(T value) throws IllegalArgumentException {
        return true;
    }

    public IgnoreInputValidator getInstance() {
        if (instance == null) {
            instance = new IgnoreInputValidator();
        }
        return instance;
    }
}
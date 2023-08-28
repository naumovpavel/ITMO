package common.utils.validators;

/**
 * Interface for fields validator
 */
public interface Validatable {
    /**
     * Method that validates fields value
     * @param value value
     * @return true if all is alright
     * @param <T> value type
     * @throws IllegalArgumentException throws if value not valid
     */
    <T> boolean validate(T value) throws IllegalArgumentException;
    Validatable getInstance();
}

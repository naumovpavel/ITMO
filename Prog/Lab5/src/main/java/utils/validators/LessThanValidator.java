package utils.validators;

import utils.Converter;

/**
 * Class that validate that fields less than certain value
 */
public class LessThanValidator implements ValidatableWithArg{
    private String value;
    public LessThanValidator(String value) {
        this.value = value;
    }
    public LessThanValidator() {
        this.value = "0";
    }

    public LessThanValidator getInstance(String value) {
        return new LessThanValidator(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> boolean validate(U value) throws IllegalArgumentException {
        U tmp = (U) Converter.convert(value.getClass(), this.value);

        if(((Comparable<U>)value).compareTo(tmp) > 0) {
            throw new IllegalArgumentException("Значение должно быть не больше " + this.value + " введено " + value);
        }
        return true;
    }

    @Override
    public Validatable getInstance() {
        return new LessThanValidator("0");
    }
}
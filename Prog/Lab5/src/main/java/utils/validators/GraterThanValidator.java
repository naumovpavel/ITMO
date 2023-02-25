package utils.validators;

import utils.Converter;

/**
 * Class that validate that field grater or equal than certain value
 */
public class GraterThanValidator implements ValidatableWithArg{
    private String value;
    public GraterThanValidator(String value) {
        this.value = value;
    }
    public GraterThanValidator() {
        this.value = "0";
    }

    public GraterThanValidator getInstance(String value) {
        return new GraterThanValidator(value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U> boolean validate(U value) throws IllegalArgumentException {
        U tmp = (U)Converter.convert(value.getClass(), this.value);

        if(((Comparable<U>)value).compareTo(tmp) <= 0) {
            throw new IllegalArgumentException("Значение должно быть больше " + this.value + " введено " + value);
        }
        return true;
    }

    @Override
    public Validatable getInstance() {
        return new GraterThanValidator("0");
    }
}
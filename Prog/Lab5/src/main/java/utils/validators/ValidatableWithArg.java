package utils.validators;

public interface ValidatableWithArg extends Validatable{
    Validatable getInstanc(String value);
}

package utils.validators;

public class NotNullValidator implements Validatable{
    private static NotNullValidator instance;

    public <T> boolean validate(T value) throws IllegalArgumentException{
        if(value == null) {
            throw new IllegalArgumentException("Значение не может быть null");
        }
        return true;
    }

    public NotNullValidator getInstanc() {
        if(instance == null) {
            instance = new NotNullValidator();
        }
        return instance;
    }

    public static NotNullValidator getInstance() {
        if(instance == null) {
            instance = new NotNullValidator();
        }
        return instance;
    }
}

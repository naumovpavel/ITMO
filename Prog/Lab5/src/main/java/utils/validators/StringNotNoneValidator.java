package utils.validators;

public class StringNotNoneValidator implements Validatable{
    private static StringNotNoneValidator instance;
    public static StringNotNoneValidator getInstance() {
        if (instance == null) {
            instance = new StringNotNoneValidator();
        }
        return instance;
    }

    public <T> boolean validate(T value) throws IllegalArgumentException {
        String s;
        try {
            s = (String) value;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Значение не является строкой");
        }
        if(value == null || s.isEmpty() || s.isBlank()) {
            throw new IllegalArgumentException("Значение не может быть пустой строкой");
        }
        return true;
    }

    @Override
    public Validatable getInstanc() {
        if (instance == null) {
            instance = new StringNotNoneValidator();
        }
        return instance;
    }
}

package exceptions;

public class UnallowedValueException  extends Exception {
    private final String field;
    private final String value;

    public UnallowedValueException(String message, String value) {
        super(message);
        this.field = message;
        this.value = value;
    }

    @Override
    public String toString() {
        return "value "+value+" for field " + field + " isn't allowed";
    }
}

package exceptions;

public class ParseException  extends Exception {
    private final String field;
    private final String value;

    public ParseException(String message, String value) {
        super(message);
        this.field = message;
        this.value = value;
    }

    @Override
    public String toString() {
        return "incorrect format value "+value+" for field " + field;
    }
}

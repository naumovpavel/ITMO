package exceptions;

public class EmptyFieldException extends Exception {
    private final String field;

    public EmptyFieldException(String message) {
        super(message);
        this.field = message;
    }

    @Override
    public String toString() {
        return "field " + field + " can't be empty";
    }
}

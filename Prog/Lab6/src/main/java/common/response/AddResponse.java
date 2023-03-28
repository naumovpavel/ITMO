package common.response;

public class AddResponse extends Response {
    public AddResponse(String answer, boolean result) {
        super(answer, result);
    }

    public AddResponse(boolean result, String error) {
        super(result, error);
    }
}

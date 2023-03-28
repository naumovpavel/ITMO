package common.response;

public class UpdateResponse extends Response{
    public UpdateResponse(String answer, boolean result) {
        super(answer, result);
    }

    public UpdateResponse(boolean result, String error) {
        super(result, error);
    }
}

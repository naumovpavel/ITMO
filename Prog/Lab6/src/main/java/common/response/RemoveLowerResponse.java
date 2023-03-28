package common.response;

public class RemoveLowerResponse extends Response{
    public RemoveLowerResponse(String answer, boolean result) {
        super(answer, result);
    }

    public RemoveLowerResponse(boolean result, String error) {
        super(result, error);
    }
}

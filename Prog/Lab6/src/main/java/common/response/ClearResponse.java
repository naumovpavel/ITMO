package common.response;

public class ClearResponse extends Response{
    public ClearResponse(String answer, boolean result) {
        super(answer, result);
    }

    public ClearResponse(boolean result, String error) {
        super(result, error);
    }
}

package common.response;

public class RemoveByIdResponse extends Response{
    public RemoveByIdResponse(String answer, boolean result) {
        super(answer, result);
    }

    public RemoveByIdResponse(boolean result, String error) {
        super(result, error);
    }
}

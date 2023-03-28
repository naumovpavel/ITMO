package common.response;

public class InfoResponse extends Response{
    public InfoResponse(String answer, boolean result) {
        super(answer, result);
    }

    public InfoResponse(boolean result, String error) {
        super(result, error);
    }
}

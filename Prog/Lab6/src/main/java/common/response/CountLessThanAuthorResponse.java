package common.response;

public class CountLessThanAuthorResponse extends Response{
    private int count;

    public CountLessThanAuthorResponse(String answer, boolean result, int count) {
        super(answer, result);
        this.count = count;
    }

    public CountLessThanAuthorResponse(boolean result, String error) {
        super(result, error);
    }

    public int getCount() {
        return count;
    }
}

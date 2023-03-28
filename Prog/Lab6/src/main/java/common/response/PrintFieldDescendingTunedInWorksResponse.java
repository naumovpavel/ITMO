package common.response;

import java.util.ArrayList;

public class PrintFieldDescendingTunedInWorksResponse extends Response{
    private ArrayList<Integer> array;

    public PrintFieldDescendingTunedInWorksResponse(String answer, boolean result, ArrayList<Integer> array) {
        super(answer, result);
        this.array = array;
    }

    public PrintFieldDescendingTunedInWorksResponse(boolean result, String error) {
        super(result, error);
    }

    public ArrayList<Integer> getArray() {
        return array;
    }
}

package common.request;

import common.Commands;

public class ClearRequest extends Request{
    public ClearRequest() {
        super(Commands.CLEAR);
    }
}

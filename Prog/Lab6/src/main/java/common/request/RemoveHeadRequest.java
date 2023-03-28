package common.request;

import common.Commands;

public class RemoveHeadRequest extends Request{
    public RemoveHeadRequest() {
        super(Commands.REMOVE_LOWER);
    }
}

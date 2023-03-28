package common.request;

import common.Commands;

public class MaxByNameRequest extends Request{
    public MaxByNameRequest() {
        super(Commands.MAX_BY_NAME);
    }
}

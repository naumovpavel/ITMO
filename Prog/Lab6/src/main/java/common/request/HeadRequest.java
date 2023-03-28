package common.request;

import common.Commands;
import common.models.Model;

public class HeadRequest extends Request{
    public HeadRequest() {
        super(Commands.HEAD);
    }
}

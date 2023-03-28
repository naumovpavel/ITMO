package common.request;

import common.Commands;

public class InfoRequest extends Request{
    public InfoRequest() {
        super(Commands.INFO);
    }
}

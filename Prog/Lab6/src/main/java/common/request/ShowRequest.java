package common.request;

import common.Commands;
import common.models.Model;

import java.util.Collection;

public class ShowRequest extends Request {

    public ShowRequest() {
        super(Commands.SHOW);
    }
}
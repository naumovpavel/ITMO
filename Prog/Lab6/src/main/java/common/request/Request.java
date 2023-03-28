package common.request;

import common.Commands;
import common.models.Model;

import java.io.Serializable;

public class Request implements Serializable {
    protected Commands type;

    public Request(Commands type) {
        this.type = type;
    }

    public Commands getType() {
        return type;
    }
}

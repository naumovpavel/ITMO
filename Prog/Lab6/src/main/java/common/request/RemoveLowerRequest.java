package common.request;

import common.Commands;
import common.models.Model;

public class RemoveLowerRequest extends Request{
    private Model model;

    public RemoveLowerRequest(Model model) {
        super(Commands.REMOVE_LOWER);
        this.model = model;
    }

    public Model getModel() {
        return model;
    }
}

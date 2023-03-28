package common.request;

import common.Commands;
import common.models.Model;

public class AddRequest extends Request{
    private Model model;

    public AddRequest(Model model) {
        super(Commands.ADD);
        this.model = model;
    }

    public Model getModel() {
        return model;
    }
}

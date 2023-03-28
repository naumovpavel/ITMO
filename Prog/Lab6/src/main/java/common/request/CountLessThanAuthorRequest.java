package common.request;

import common.Commands;
import common.models.Model;

public class CountLessThanAuthorRequest extends Request{
    private Model model;

    public CountLessThanAuthorRequest(Model model) {
        super(Commands.COUNT_LESS_THAN_AUTHOR);
        this.model = model;
    }

    public Model getModel() {
        return model;
    }
}

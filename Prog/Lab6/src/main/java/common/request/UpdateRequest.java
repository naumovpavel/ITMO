package common.request;

import common.Commands;
import common.models.Model;

public class UpdateRequest extends Request{
    private Model model;
    private Long id;

    public UpdateRequest(Model model, Long id) {
        super(Commands.UPDATE);
        this.model = model;
        this.id = id;
    }

    public Model getModel() {
        return model;
    }

    public Long getId() {
        return id;
    }
}

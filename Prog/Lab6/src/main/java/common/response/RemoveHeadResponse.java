package common.response;

import common.models.Model;

public class RemoveHeadResponse extends Response{
    private Model model;

    public RemoveHeadResponse(String answer, boolean result, Model model) {
        super(answer, result);
        this.model = model;
    }

    public RemoveHeadResponse(boolean result, String error) {
        super(result, error);
    }

    public Model getModel() {
        return model;
    }
}

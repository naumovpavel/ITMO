package common.response;

import common.models.Model;

public class MaxByNameResponse extends Response{
    private Model model;

    public MaxByNameResponse(String answer, boolean result, Model model) {
        super(answer, result);
        this.model = model;
    }

    public MaxByNameResponse(boolean result, String error) {
        super(result, error);
    }

    public Model getModel() {
        return model;
    }
}

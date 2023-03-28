package common.response;

import common.models.Model;

public class GerByIdResponse extends Response{
    private Model model;

    public GerByIdResponse(String answer, boolean result, Model model) {
        super(answer, result);
        this.model = model;
    }

    public GerByIdResponse(boolean result, String error) {
        super(result, error);
    }

    public Model getModel() {
        return model;
    }
}

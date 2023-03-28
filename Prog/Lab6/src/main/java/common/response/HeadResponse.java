package common.response;

import common.models.Model;

public class HeadResponse extends Response{
    private Model model;

    public HeadResponse(String answer, boolean result, Model model) {
        super(answer, result);
        this.model = model;
    }

    public HeadResponse(boolean result, String error) {
        super(result, error);
    }

    public Model getModel() {
        return model;
    }
}

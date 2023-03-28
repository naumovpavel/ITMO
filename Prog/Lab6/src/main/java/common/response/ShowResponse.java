package common.response;

import common.models.Model;

import java.util.Collection;

public class ShowResponse extends Response {
    protected Collection<Model> collection;

    public ShowResponse(String answer, boolean result, Collection<Model> collection) {
        super(answer, result);
        this.collection = collection;
    }

    public ShowResponse(boolean result, String error) {
        super(result, error);
    }

    public Collection<Model> getCollection() {
        return collection;
    }
}

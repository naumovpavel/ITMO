package common.request;

import common.Commands;
import common.models.Model;

public class GetByIdRequest extends Request{
    private Long id;

    public GetByIdRequest(Long id) {
        super(Commands.GET_BY_ID);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

package common.request;

import common.Commands;

public class RemoveByIdRequest extends Request{
    private Long id;

    public RemoveByIdRequest(Long id) {
        super(Commands.REMOVE_BY_ID);
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

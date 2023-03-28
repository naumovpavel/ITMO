package server.сommands;

import common.request.GetByIdRequest;
import common.request.Request;
import common.response.GerByIdResponse;
import common.response.Response;
import common.utils.ModelTree;
import server.handlers.CollectionHandler;

public class GetById extends Command {
    private final CollectionHandler<?, ?> handler;
    private final ModelTree tree;

    public GetById(CollectionHandler<?, ?> handler) {
        super("get_by_id", "Добавляет новый элемент в коллекцию");
        this.handler = handler;
        this.tree = handler.getTree();
    }

    @Override
    public Response execute(Request request) {
        if(handler.hasId(((GetByIdRequest)request).getId())) {
            return new GerByIdResponse("Успешно", true, handler.getById(((GetByIdRequest) request).getId()));
        } else {
            return new GerByIdResponse(false, "Объекта с таким id не существует");
        }
    }
}

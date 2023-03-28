package server.сommands;

import common.request.Request;
import common.request.UpdateRequest;
import common.response.Response;
import common.response.UpdateResponse;
import common.utils.ModelTree;
import server.handlers.CollectionHandler;

public class Update extends Command{
    private final CollectionHandler<?, ?> handler;
    private final ModelTree tree;

    public Update(CollectionHandler<?, ?> handler) {
        super("update", "Добавляет новый элемент в коллекцию");
        this.handler = handler;
        this.tree = handler.getTree();
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        boolean result = handler.update(((UpdateRequest) request).getId(), ((UpdateRequest) request).getModel());
        if(result) {
            return new UpdateResponse("Объект успешно обновлен", true);
        } else {
            return new UpdateResponse( false, "Объекта с таким id не найдено");
        }
    }
}

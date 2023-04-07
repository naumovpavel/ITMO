package server.сommands;

import common.network.Status;
import common.network.Request;
import common.network.Response;
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
        if(handler.hasId(((Long) request.get("id")))) {
            return new Response("Успешно", Status.OK).put("model", handler.getById((Long) request.get("id")));
        } else {
            return new Response("Объекта с таким id не существует", Status.OK);
        }
    }
}

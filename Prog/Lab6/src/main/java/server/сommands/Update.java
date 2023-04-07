package server.сommands;

import common.network.Status;
import common.models.Model;
import common.network.Request;
import common.network.Response;
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
        boolean result = handler.update((Long) request.get("id"), (Model) request.get("model"));
        if(result) {
            return new Response("Объект успешно обновлен", Status.OK);
        } else {
            return new Response( "Объекта с таким id не найдено", Status.OK);
        }
    }
}

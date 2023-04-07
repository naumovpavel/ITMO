package server.сommands;

import common.network.Status;
import common.network.Request;
import common.network.Response;
import server.handlers.CollectionHandler;

/**
 * Remove by id command
 */
public class RemoveById extends Command {
    private final CollectionHandler<?, ?> handler;

    public RemoveById(CollectionHandler<?, ?> handler) {
        super("remove_by_id id", "Удаляет элемент с заданным id");
        this.handler = handler;
    }

    @Override
    Response execute(Request request) {
        if(handler.removeById((Long) request.get("id"))) {
            return new Response("Элемент успешно удален", Status.OK);
        } else {
            return new Response("Элемента с таким id не было в коллекции", Status.OK);
        }
    }
}

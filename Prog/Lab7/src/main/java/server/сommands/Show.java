package server.сommands;

import common.network.Status;
import common.network.Request;
import common.network.Response;
import server.handlers.CollectionHandler;

/**
 * Show command
 */
public class Show extends Command {
    private final CollectionHandler<?, ?> handler;

    public Show(CollectionHandler<?, ?> handler) {
        super("show", "Выводит все элементы коллекции");
        this.handler = handler;
    }

    @SuppressWarnings("unchecked")
    @Override
    Response execute(Request request) throws IllegalArgumentException {
        if(handler.getCollection().size() == 0) {
            return new Response("Коллекция пуста", Status.OK);
        }

        return new Response("", Status.OK).put("collection", handler.getCollection().toArray());
    }
}

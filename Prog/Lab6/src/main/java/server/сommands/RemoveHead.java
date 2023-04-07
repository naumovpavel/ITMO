package server.сommands;

import common.network.Status;
import common.network.Request;
import common.network.Response;
import server.handlers.CollectionHandler;

/**
 * Remove head command
 */
public class RemoveHead extends Command {
    private final CollectionHandler<?, ?> handler;

    public RemoveHead(CollectionHandler<?, ?> handler) {
        super("remove_head", "Удаляет первый элемент коллекции");
        this.handler = handler;
    }

    @Override
    Response execute(Request request) {
        if(handler.getCollection().size() == 0) {
            return new Response("Коллекция пуста", Status.OK);
        } else {
            return new Response("", Status.OK).put("model", handler.removeHead());
        }
    }
}

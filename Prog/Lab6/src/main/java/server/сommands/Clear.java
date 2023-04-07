package server.сommands;

import common.network.Request;
import common.network.Response;
import common.network.Status;
import server.handlers.CollectionHandler;

/**
 * Clear command
 */
public class Clear extends Command {
    private final CollectionHandler<?, ?> handler;

    public Clear(CollectionHandler<?, ?> handler) {
        super("clear", "Очищает коллекцию");
        this.handler = handler;
    }

    @Override
    Response execute(Request request) {
        handler.clear();
        return new Response("Коллекция очищена", Status.OK);
    }
}

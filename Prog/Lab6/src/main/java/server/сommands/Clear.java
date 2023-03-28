package server.сommands;

import common.request.Request;
import common.response.ClearResponse;
import common.response.Response;
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
        return new ClearResponse("Коллекция очищена", true);
    }
}

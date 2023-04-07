package server.сommands;

import common.network.Status;
import common.network.Request;
import common.network.Response;
import server.handlers.CollectionHandler;

/**
 * Save command
 */
public class Save extends Command {
    private final CollectionHandler<?, ?> handler;

    public Save(CollectionHandler<?, ?> handler) {
        super("save", "Сохраняет коллекцию");
        this.handler = handler;
    }

    @Override
    Response execute(Request request) throws IllegalArgumentException {
        if(handler.save())
            return new Response("Коллекция успешно сохранена", Status.OK);
        return new Response("Возникли ошибки при сохранение", Status.OK);
    }
}

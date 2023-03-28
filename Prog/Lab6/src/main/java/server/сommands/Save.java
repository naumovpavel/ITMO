package server.сommands;

import common.request.Request;
import common.response.Response;
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
            return new Response("Коллекция успешно сохранена", true);
        return new Response(false, "Возникли ошибки при сохранение");
    }
}

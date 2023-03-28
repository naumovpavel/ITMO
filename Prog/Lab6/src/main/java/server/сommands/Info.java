package server.сommands;

import common.request.Request;
import common.response.InfoResponse;
import common.response.Response;
import server.handlers.CollectionHandler;

/**
 * Info command
 */
public class Info extends Command {
    private final CollectionHandler<?, ?> handler;

    public Info(CollectionHandler<?, ?> handler) {
        super("info", "Выводит информацию о коллекции");
        this.handler = handler;
    }

    @Override
    Response execute(Request request) {
        return new InfoResponse(String.format("Дата инициализации : %s, тип : %s, количество элементов %d", handler.getInitializationDate(), handler.getType().getSimpleName(), handler.getCollection().size()), true);
    }
}

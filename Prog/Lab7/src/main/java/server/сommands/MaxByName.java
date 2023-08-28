package server.сommands;

import common.network.Status;
import common.network.Request;
import common.network.Response;
import server.handlers.CollectionHandler;

/**
 * Max by name command
 */
public class MaxByName extends Command {
    private final CollectionHandler<?, ?> handler;

    public MaxByName(CollectionHandler<?, ?> handler) {
        super("max_by_name", "Выводит любой объект из коллекции, значение поля name которого является максимальным");
        this.handler = handler;
    }

    @Override
    Response execute(Request request) {
        if(handler.getCollection().size() == 0) {
            return new Response("Коллекция пуста", Status.OK);
        } else {
            return new Response("", Status.OK).put("model", handler.maxByName());
        }
    }
}

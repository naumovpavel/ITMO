package server.сommands;

import common.request.Request;
import common.response.MaxByNameResponse;
import common.response.Response;
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
            return new MaxByNameResponse(false, "Коллекция пуста");
        } else {
            return new MaxByNameResponse("", true, handler.maxByName());
        }
    }
}

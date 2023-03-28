package server.сommands;

import common.request.Request;
import common.response.HeadResponse;
import common.response.Response;
import server.handlers.CollectionHandler;

/**
 * Head command
 */
public class Head extends Command {
    private final CollectionHandler<?, ?> handler;

    public Head(CollectionHandler<?, ?> handler) {
        super("head", "Выводит первый элемент в коллекции");
        this.handler = handler;
    }

    @Override
    Response execute(Request request) throws IllegalArgumentException {
        if(handler.getHead() != null) {
            return new HeadResponse("", true, handler.getHead());
        } else {
            return new HeadResponse(false, "Коллекция пуста");
        }
    }
}

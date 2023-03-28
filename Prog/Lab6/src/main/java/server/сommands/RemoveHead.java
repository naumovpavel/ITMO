package server.сommands;

import common.request.Request;
import common.response.RemoveHeadResponse;
import common.response.Response;
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
            return new RemoveHeadResponse( false, "Коллекция пуста");
        } else {
            return new RemoveHeadResponse("", true, handler.removeHead());
        }
    }
}

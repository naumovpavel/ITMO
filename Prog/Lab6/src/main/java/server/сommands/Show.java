package server.сommands;

import common.models.Model;
import common.request.Request;
import common.response.Response;
import common.response.ShowResponse;
import server.handlers.CollectionHandler;

import java.util.Collection;

/**
 * Show command
 */
public class Show extends Command {
    private final CollectionHandler<?, ?> handler;

    public Show(CollectionHandler<?, ?> handler) {
        super("show", "Выводит все элементы коллекции");
        this.handler = handler;
    }

    @SuppressWarnings("unchecked")
    @Override
    Response execute(Request request) throws IllegalArgumentException {
        if(handler.getCollection().size() == 0) {
            return new ShowResponse(false, "Коллекция пуста");
        }

        return new ShowResponse("", true, (Collection<Model>) handler.getCollection());
    }
}

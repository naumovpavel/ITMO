package server.сommands;

import common.models.Model;
import common.network.Status;
import common.network.Request;
import common.network.Response;
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
        Collection<? extends Model> collection = handler.show(request.getUser().getId());
        return new Response("", Status.OK).put("collection", collection);
    }
}

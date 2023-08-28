package server.сommands;

import common.network.Status;
import common.network.Request;
import common.network.Response;
import server.handlers.CollectionHandler;

import java.util.ResourceBundle;

/**
 * Remove by id command
 */
public class RemoveById extends Command {
    private final CollectionHandler<?, ?> handler;

    public RemoveById(CollectionHandler<?, ?> handler) {
        super("remove_by_id id", "Удаляет элемент с заданным id");
        this.handler = handler;
    }

    @Override
    Response execute(Request request) {
        var r = ResourceBundle.getBundle("bundles.ServerAnswers", request.getLocale());
        if(handler.removeById((int) request.get("id"), request.getUser().getId())) {
            return new Response(r.getString("remove"), Status.OK);
        } else {
            return new Response(r.getString("remove_error"), Status.ERROR);
        }
    }
}

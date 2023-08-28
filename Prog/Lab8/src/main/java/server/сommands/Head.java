package server.сommands;

import common.network.Status;
import common.network.Request;
import common.network.Response;
import server.handlers.CollectionHandler;

import java.util.ResourceBundle;

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
            return new Response(Status.OK).put("model", handler.getHead());
        } else {
            var r = ResourceBundle.getBundle("bundles.ServerAnswers", request.getLocale());
            return new Response(r.getString("head_error"), Status.OK);
        }
    }
}

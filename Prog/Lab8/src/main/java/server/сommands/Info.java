package server.сommands;

import common.network.Status;
import common.network.Request;
import common.network.Response;
import server.handlers.CollectionHandler;

import java.util.ResourceBundle;

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
        var r = ResourceBundle.getBundle("bundles.ServerAnswers", request.getLocale());
        return new Response(String.format(r.getString("info"), handler.getInitializationDate(), handler.getType().getSimpleName(), handler.getCollection().size()), Status.OK);
    }
}

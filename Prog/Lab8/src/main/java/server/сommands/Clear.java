package server.сommands;

import common.network.Request;
import common.network.Response;
import common.network.Status;
import server.handlers.CollectionHandler;

import java.util.ResourceBundle;

/**
 * Clear command
 */
public class Clear extends Command {
    private final CollectionHandler<?, ?> handler;

    public Clear(CollectionHandler<?, ?> handler) {
        super("clear", "Очищает коллекцию");
        this.handler = handler;
    }

    @Override
    Response execute(Request request) {
        var r = ResourceBundle.getBundle("bundles.ServerAnswers", request.getLocale());
        if(handler.clear(request.getUser().getId()))
            return new Response(r.getString("clear"), Status.OK);
        else
            return new Response(r.getString("clear_error"), Status.ERROR);

    }
}

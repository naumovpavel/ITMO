package server.сommands;

import common.models.Model;
import common.network.Request;
import common.network.Response;
import common.network.Status;
import server.handlers.CollectionHandler;
import common.utils.ModelTree;

/**
 * Add command
 */
public class GetEvents extends Command {
    private final CollectionHandler<?, ?> handler;

    public GetEvents(CollectionHandler<?, ?> handler) {
        super("get_events", "");
        this.handler = handler;
    }

    @Override
    public Response execute(Request request) {
        return new Response("", Status.OK)
                .put("events", handler.getEventHandler().getEvents(request.getUser().getId()));
    }
}

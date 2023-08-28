package server.сommands;

import common.network.Status;
import common.models.Model;
import common.network.Request;
import common.network.Response;
import common.utils.ModelTree;
import server.handlers.CollectionHandler;

import java.util.ResourceBundle;

public class Update extends Command{
    private final CollectionHandler<?, ?> handler;
    private final ModelTree tree;

    public Update(CollectionHandler<?, ?> handler) {
        super("update", "Добавляет новый элемент в коллекцию");
        this.handler = handler;
        this.tree = handler.getTree();
    }

    @Override
    public Response execute(Request request) throws IllegalArgumentException {
        var r = ResourceBundle.getBundle("bundles.ServerAnswers", request.getLocale());
        boolean result = handler.update((int) request.get("id"), (Model) request.get("model"), request.getUser().getId());
        if(result) {
            return new Response(r.getString("update"), Status.OK);
        } else {
            return new Response( r.getString("remove_error"), Status.OK);
        }
    }
}

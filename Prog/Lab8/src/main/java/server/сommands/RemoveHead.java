package server.сommands;

import common.models.Model;
import common.network.Status;
import common.network.Request;
import common.network.Response;
import server.handlers.CollectionHandler;

import java.util.ResourceBundle;

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
        var r = ResourceBundle.getBundle("bundles.ServerAnswers", request.getLocale());
        if(handler.getCollection().size() == 0) {
            return new Response(r.getString("head_error"), Status.OK);
        } else {
            Model model = handler.removeHead(request.getUser().getId());
            if(model != null)
                return new Response(Status.OK).put("model", model);
            else
                return new Response(r.getString("remove_error"), Status.ERROR);
        }
    }
}

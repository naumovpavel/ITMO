package server.сommands;

import common.models.Model;
import common.network.Status;
import common.network.Request;
import common.network.Response;
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
            return new Response("Коллекция пуста", Status.OK);
        } else {
            Model model = handler.removeHead(request.getUser().getId());
            if(model != null)
                return new Response(Status.OK).put("model", model);
            else
                return new Response("Элемента с таким id не было в коллекции или он не принадлежал вам", Status.ERROR);
        }
    }
}

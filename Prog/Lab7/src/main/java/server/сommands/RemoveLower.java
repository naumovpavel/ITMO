package server.сommands;

import common.network.Status;
import common.models.Model;
import common.network.Request;
import common.network.Response;
import server.handlers.CollectionHandler;
import common.utils.ModelTree;

/**
 * Remove lower command
 */
public class RemoveLower extends Command {
    private final CollectionHandler<?, ?> handler;
    private final ModelTree tree;

    public RemoveLower(CollectionHandler<?, ?> handler) {
        super("remove_lower {element}", "Удаляет из коллекции все элементы, меньшие, чем заданный");
        this.handler = handler;
        this.tree = handler.getTree();
    }

    @Override
    Response execute(Request request) {
        if(handler.getCollection().size() == 0) {
            return new Response( "Коллекция пуста", Status.OK);
        } else {
            if(handler.removeLower((Model)request.get("model"), request.getUser().getId()))
                return new Response("Объекты успешно удалены", Status.OK);
            else
                return new Response("Не все объекты были удалены", Status.ERROR);
        }
    }
}

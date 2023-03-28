package server.сommands;

import common.request.RemoveLowerRequest;
import common.request.Request;
import common.response.RemoveLowerResponse;
import common.response.Response;
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
            return new RemoveLowerResponse( false, "Коллекция пуста");
        } else {
            handler.removeLower(((RemoveLowerRequest)request).getModel());
            return new RemoveLowerResponse("Объекты успешно удалены", true);
        }
    }
}

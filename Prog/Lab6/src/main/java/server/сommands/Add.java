package server.сommands;

import client.utils.input.Builder;
import common.request.AddRequest;
import common.request.Request;
import common.response.AddResponse;
import common.response.Response;
import server.handlers.CollectionHandler;
import common.utils.ModelTree;

/**
 * Add command
 */
public class Add extends Command {
    private final CollectionHandler<?, ?> handler;
    private final ModelTree tree;

    public Add(CollectionHandler<?, ?> handler) {
        super("add", "Добавляет новый элемент в коллекцию");
        this.handler = handler;
        this.tree = handler.getTree();
    }

    @Override
    public Response execute(Request request) {
        ((AddRequest)request).getModel().autoGen();
        handler.add(((AddRequest) request).getModel());
        return new AddResponse("Объект успешно добавлен", true);
    }
}

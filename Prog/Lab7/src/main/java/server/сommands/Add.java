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
        Model model = request.get("model");
        if(handler.add(model, request.getUser().getId()))
            return new Response("Объект успешно добавлен с id = " + model.getId(), Status.OK);
        else
            return new Response("Объект не был добавлен", Status.ERROR);
    }
}

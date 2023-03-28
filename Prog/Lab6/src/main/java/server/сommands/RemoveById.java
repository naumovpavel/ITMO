package server.сommands;

import common.request.RemoveByIdRequest;
import common.request.Request;
import common.response.RemoveByIdResponse;
import common.response.Response;
import server.handlers.CollectionHandler;
import common.utils.Converter;

/**
 * Remove by id command
 */
public class RemoveById extends Command {
    private final CollectionHandler<?, ?> handler;

    public RemoveById(CollectionHandler<?, ?> handler) {
        super("remove_by_id id", "Удаляет элемент с заданным id");
        this.handler = handler;
    }

    @Override
    Response execute(Request request) {
        if(handler.removeById(((RemoveByIdRequest) request).getId())) {
            return new RemoveByIdResponse("Элемент успешно удален", true);
        } else {
            return new RemoveByIdResponse(false, "Элемента с таким id не было в коллекции");
        }
    }
}

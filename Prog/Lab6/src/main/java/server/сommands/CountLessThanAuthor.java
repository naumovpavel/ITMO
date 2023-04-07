package server.сommands;

import common.network.Request;
import common.network.Response;
import common.network.Status;
import server.handlers.CollectionHandler;
import common.models.Person;

/**
 * Count less than author command
 */
public class CountLessThanAuthor extends Command {
    private final CollectionHandler<?, ?> handler;

    public CountLessThanAuthor(CollectionHandler<?, ?> handler) {
        super("count_less_than_author author", "Выводит количество элементов, значение поля author которых меньше заданного");
        this.handler = handler;
    }

    @Override
    Response execute(Request request) {
        return new Response("", Status.OK).put("count", handler.countLess(((Person) request.get("model"))));
    }
}

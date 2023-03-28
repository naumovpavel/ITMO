package server.сommands;

import common.request.CountLessThanAuthorRequest;
import common.request.Request;
import common.response.CountLessThanAuthorResponse;
import common.response.Response;
import common.utils.ModelTree;
import server.handlers.CollectionHandler;
import common.models.Person;
import common.utils.ModelTree;

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
        return new CountLessThanAuthorResponse("", true, handler.countLess(((Person) ((CountLessThanAuthorRequest)request).getModel())));
    }
}

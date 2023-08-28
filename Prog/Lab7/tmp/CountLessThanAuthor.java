package server.сommands;

import common.request.Request;
import common.response.Response;
import common.utils.ModelTree;
import server.handlers.CollectionHandler;
import common.models.Person;
import server.utils.ModelTree;

/**
 * Count less than author command
 */
public class CountLessThanAuthor extends Command {
    private final CollectionHandler<?, ?> handler;
    private final ModelTree tree;

    public CountLessThanAuthor(CollectionHandler<?, ?> handler) {
        super("count_less_than_author author", "Выводит количество элементов, значение поля author которых меньше заданного");
        this.handler = handler;
        this.tree = new ModelTree(Person.class);
    }

    @Override
    Response execute(Request request) throws IllegalArgumentException {
        return null;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        //System.out.println(handler.countLess(builder.build(tree)));
    }
}

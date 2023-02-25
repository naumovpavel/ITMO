package сommands;

import handlers.CollectionHandler;
import models.Person;
import utils.input.Builder;
import utils.ModelTree;

/**
 * Count less than author command
 */
public class CountLessThanAuthor extends Command {
    private final CollectionHandler<?, ?> handler;
    private final ModelTree tree;
    private final Builder builder;

    public CountLessThanAuthor(CollectionHandler<?, ?> handler, Builder builder) {
        super("count_less_than_author author", "Выводит количество элементов, значение поля author которых меньше заданного");
        this.handler = handler;
        this.tree = new ModelTree(Person.class);
        this.builder = builder;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        System.out.println(handler.countLess(builder.build(tree)));
    }
}

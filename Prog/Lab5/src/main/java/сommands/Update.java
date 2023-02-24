package сommands;

import handlers.CollectionHandler;
import utils.input.Builder;
import utils.Converter;
import utils.ModelTree;

public class Update extends Command {
    private final CollectionHandler<?, ?> handler;
    private final ModelTree tree;
    private final Builder builder;

    public Update(CollectionHandler<?, ?> handler, Builder builder) {
        super("update id", "Обновляет значение элемента коллекции, id которого равен заданному");
        this.handler = handler;
        this.tree = handler.getTree();
        this.builder = builder;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        if(args.length < 2 || args[1].isEmpty() || args[1].isBlank()) {
            throw new IllegalArgumentException("Вы не ввелм значение id");
        }
        Long id = Converter.convert(Long.class, args[1]);
        if(handler.hasId(id)) {
            handler.update(builder.build(tree, id), id);
            System.out.println("Объект успешно добавлен");
        } else {
            System.out.println("Объекта с данным id не существует");
        }
    }
}

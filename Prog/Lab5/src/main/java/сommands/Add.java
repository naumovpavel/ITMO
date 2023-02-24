package сommands;

import handlers.CollectionHandler;
import utils.input.Builder;
import utils.ModelTree;

public class Add extends Command {
    private final CollectionHandler<?, ?> handler;
    private final ModelTree tree;
    private final Builder builder;

    public Add(CollectionHandler<?, ?> handler, Builder builder) {
        super("add", "Добавляет новый элемент в коллекцию");
        this.handler = handler;
        this.tree = handler.getTree();
        this.builder = builder;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        handler.add(builder.build(tree));
        System.out.println("Объект успешно добавлен");
    }
}

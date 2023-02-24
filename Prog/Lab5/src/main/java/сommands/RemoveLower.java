package сommands;

import handlers.CollectionHandler;
import utils.input.Builder;
import utils.ModelTree;

public class RemoveLower extends Command {
    private final CollectionHandler<?, ?> handler;
    private final ModelTree tree;
    private final Builder builder;

    public RemoveLower(CollectionHandler<?, ?> handler, Builder builder) {
        super("remove_lower {element}", "Удаляет из коллекции все элементы, меньшие, чем заданный");
        this.handler = handler;
        this.tree = handler.getTree();
        this.builder = builder;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        handler.removeLower(builder.build(tree));
        System.out.println("Объекты успешно удалены");
    }
}

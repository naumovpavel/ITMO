package сommands;

import handlers.CollectionHandler;

public class Clear extends Command {
    private final CollectionHandler<?, ?> handler;

    public Clear(CollectionHandler<?, ?> handler) {
        super("clear", "Очищает коллекцию");
        this.handler = handler;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        handler.clear();
        System.out.println("Коллекция очищена");
    }
}

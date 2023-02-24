package сommands;

import handlers.CollectionHandler;

public class Save extends Command {
    private final CollectionHandler<?, ?> handler;

    public Save(CollectionHandler<?, ?> handler) {
        super("save", "Сохраняет коллекцию");
        this.handler = handler;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        handler.save();
        System.out.println("Коллекция успешно сохранена");
    }
}

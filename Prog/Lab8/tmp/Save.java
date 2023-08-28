package server.сommands;

import server.handlers.CollectionHandler;

/**
 * Save command
 */
public class Save extends Command {
    private final CollectionHandler<?, ?> handler;

    public Save(CollectionHandler<?, ?> handler) {
        super("save", "Сохраняет коллекцию");
        this.handler = handler;
    }

    @Override
    void execute(String[] args) throws IllegalArgumentException {
        if(handler.save())
            System.out.println("Коллекция успешно сохранена");
    }
}
